package com.trelloiii.repository

import com.trelloiii.UrlNotFoundException
import groovy.sql.Sql
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

import java.time.LocalDate
import java.time.LocalDateTime

@Component
class UrlRepository {
    private final Map<String,String> myDataSource
    private def logger = LoggerFactory.getLogger(UrlRepository.class)
    def getSql = {
        Sql.newInstance(myDataSource['url'],myDataSource['user'],myDataSource['password'])
    }
    UrlRepository(Map<String, String> myDataSource) {
        this.myDataSource = myDataSource
    }
    @Scheduled(fixedDelay = 259200L)
    def cleanOldShortners(){
        logger.info('Time to clean old rows in db')
        def sql = getSql()
        sql.execute 'delete from url u where u.lifetime<current_timestamp'
        sql.close()
    }
    def saveUrl(String inputUrl,String hashedUrl){
        def sql = getSql()
        sql.withTransaction {
            def row = sql.firstRow("select * from url u where u.input_url = :url",[url:inputUrl])
            def time = LocalDateTime.now().plusMonths(1)
            if(row==null || row.isEmpty()){
                sql.execute "insert into url (input_url,redirect_postfix,lifetime) values (?1,?2,?3)",[inputUrl,hashedUrl,time]
            }
        }
        sql.close()
    }
    def getUrlByHash(String hash){
        def sql = getSql()
        def row = sql.firstRow "select input_url from url where redirect_postfix=?1",[hash]
        sql.close()
        if(row==null || row.isEmpty())
            throw new UrlNotFoundException()
        return row.get("input_url")
    }
}

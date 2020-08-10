package com.trelloiii.service

import com.trelloiii.repository.UrlRepository
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.DigestUtils

@Component
@CompileStatic
class UrlService {
    private final UrlRepository urlRepository
    @Value('${server.host}')
    def serverHost
    @Autowired
    UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository
    }

    private def checkForUrl(String url){
        def matching = url=~/^(https?|ftp|file):\/\/[-a-zA-Z0-9+&@#%?=~_|!:,.;]*[-a-zA-Z0-9+&@#\/%=~_|]/
        return matching.size()!=0
    }
    def processLink(String link){
        if(checkForUrl(link)){
            def hashed = hashLink(link)
            urlRepository.saveUrl(link,hashLink(link))
            return "$serverHost/$hashed"
        }else{
            return "Produced string is not URL"
        }
    }
    def getUrlByHash(String hash){
        return urlRepository.getUrlByHash(hash)
    }
    private String hashLink(String link){
        return DigestUtils.md5DigestAsHex(link.bytes).substring(0,6)
    }
}

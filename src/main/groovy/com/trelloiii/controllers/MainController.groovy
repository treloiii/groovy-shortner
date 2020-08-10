package com.trelloiii.controllers

import com.trelloiii.UrlNotFoundException
import com.trelloiii.service.UrlService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView

@Controller
@RequestMapping("/")
class MainController {
    def logger = LoggerFactory.getLogger(MainController.class)
    final UrlService urlService

    @Autowired
    MainController(UrlService urlService) {
        this.urlService = urlService
    }

    @GetMapping
    def main() {
        return new ModelAndView("main")
    }

    @PostMapping("/short")
    def shortLink(@RequestParam("url") String url) {
        logger.debug('Request to short url {}', url)
        return new ModelAndView("main", [url: urlService.processLink(url)])
    }

    @GetMapping('{hash}')
    def redirect(@PathVariable String hash) {
        logger.debug('Request url by hash {}', hash)
        try {
            def url = urlService.getUrlByHash(hash)
            return new RedirectView(url)
        }catch(UrlNotFoundException e){
            logger.warn(e.message)
            return new ModelAndView("main",[error:'Requested url not found'])
        }
    }
}

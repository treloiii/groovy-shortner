package com.trelloiii

class UrlNotFoundException extends RuntimeException {
    UrlNotFoundException(String hash) {
        super("Url with hash $hash not found ")
    }
}

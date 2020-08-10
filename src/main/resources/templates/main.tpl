yieldUnescaped '<!DOCTYPE html>'
html {
    head {
        title('Spring Boot - Groovy templates example')
        link(rel: 'stylesheet', href: 'https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css')
        h1('Groovy url shortner', class: 'text-center mt-5')
        div(class: 'text-center') {
            p('Enter the url and have a shorter link!')
            form(action: '/short', method: 'post') {
                input(type: 'text', name: 'url', placeholder: 'https://google.com')
                input(type: 'submit')
            }
            if (url) {
                div(class:'mt-3'){
                    yield 'Your URL: ' a(href: url, "$url")
                }
            }
            p(class: 'mt-3', style: 'color:red;', "${error ?: ""}")
        }
    }
    body {
        script(src: 'https://code.jquery.com/jquery-3.5.1.slim.min.js')
        script(src: 'https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js')
        script(src: 'https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js')
    }
}
import ResponseStatus.ACCEPT

fun main() {
    println("Hello Word!")
}

enum class ResponseStatus {
    ACCEPT, DECLINE
}

class ExternalService {
    fun processRequest(name: String): ResponseStatus {
        return ResponseStatus.values().random()
    }
}

class MyProgram(val extService: ExternalService) {

    fun request(name: String): String {
        val response = extService.processRequest(name)
        return if (response == ACCEPT)
            "$name, your request was accepted."
        else
            "$name, your request was declined."
    }
}
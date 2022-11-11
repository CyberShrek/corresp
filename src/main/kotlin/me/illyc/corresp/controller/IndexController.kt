package me.illyc.corresp.controller

import com.vniizht.ucheck.UserCheckRemote
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import javax.naming.InitialContext
import javax.security.auth.login.LoginException
import javax.servlet.http.HttpServletRequest

@Controller
class IndexController{

    @GetMapping
    fun index() = "redirect:passengers"

    @GetMapping("/passengers")
    fun passengers(request: HttpServletRequest): String {
        checkRequest(request)
        return "index.html"
    }
}

// USER-CHECK
private const val TASK_CODE = "CORRESPWEB"
private const val EJB_NAME  = "java:global/UCheck-1.0/UserCheck!com.vniizht.ucheck.UserCheckRemote"

fun checkRequest(request: HttpServletRequest){
    val uCheck = InitialContext().lookup(EJB_NAME) as UserCheckRemote

    uCheck.setUser(request.remoteUser)
    uCheck.setIp(request.remoteAddr)
    uCheck.setTaskCode(TASK_CODE)
    uCheck.setStatTaskCode(TASK_CODE)

    if (!uCheck.check()) throw LoginException("Доступ запрещён")
}
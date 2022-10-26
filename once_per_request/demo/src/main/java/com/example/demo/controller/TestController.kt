package com.example.demo.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.net.http.HttpResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class TestController {
    @ResponseBody
    @GetMapping("/v1/forward")
    fun forward(request: HttpServletRequest, response: HttpServletResponse): String {
        request.getParameterValues("isRemainedPrevRequest");
        request.getRequestDispatcher("/v1/forward/target").forward(request, response)
        return ""
    }

    @ResponseBody
    @GetMapping("/v1/forward/target")
    fun targetForForward(request: HttpServletRequest): String {
        request.getParameterValues("isRemainedPrevRequest")
        return "123"
    }

    @ResponseBody
    @GetMapping("/v1/redirect")
    fun redirect(request: HttpServletRequest, response: HttpServletResponse): String {
        request.getParameterValues("isRemainedPrevRequest")
        response.sendRedirect("/v1/redirect/123")
        return ""
    }

    @ResponseBody
    @GetMapping("/v1/redirect/target")
    fun targetForRedirect(request: HttpServletRequest): String {
        request.getParameterValues("isRemainedPrevRequest")
        return "123"
    }


}
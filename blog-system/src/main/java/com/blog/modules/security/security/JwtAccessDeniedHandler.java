package com.blog.modules.security.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 拒绝访问处理程序
 *
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

   @Override
   public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
      //当用户在没有授权的情况下访问受保护的REST资源时，将调用此方法发送403 Forbidden响应
      response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
   }
}

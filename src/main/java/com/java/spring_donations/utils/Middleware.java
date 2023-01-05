package com.java.spring_donations.utils;



import com.java.spring_donations.constants.CommonConstants;
import com.java.spring_donations.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

public class Middleware {

    public  static boolean middleware( HttpServletRequest request){;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
        if (Objects.nonNull(user)) {
            return true;
        } else {
            return false;
        }
    }

    public  static boolean middlewareAdmin( HttpServletRequest request){;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CommonConstants.SESSION_ADMIN);
        if (Objects.nonNull(user)) {
            return true;
        } else {
            return false;
        }
    }
}

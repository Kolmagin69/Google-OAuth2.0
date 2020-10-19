<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <style>
       .button-1 {
           color: #232323;
           background: transparent;
           border: 1px solid #232323;
           position: relative;
           font-size: 14px;
           letter-spacing: .3em;
           font-family: 'Montserrat', sans-serif;
           padding: 17px 34px 17px 39px;
           transition: .2s ease-in-out;
           cursor: pointer;
       }

       .button-1:before {
           content: "";
           position: absolute;
           top: 5px;
           left: 5px;
           width: 100%;
           height: 100%;
           background: #32D4E2;
           z-index: -1;
           transition: .25s ease;
           transform: translate(0, 0);
       }

       .button-1:hover:before {
           transform: translate(4px, 4px);
       }
       .button-2 {
                  color: #232323;
                  background: transparent;
                  border: 1px solid #232323;
                  position: relative;
                  left: 800px;
                  font-size: 14px;
                  letter-spacing: .3em;
                  font-family: 'Montserrat', sans-serif;
                  padding: 17px 34px 17px 39px;
                  transition: .2s ease-in-out;
                  cursor: pointer;
              }

        .d9 h3 {
          text-align: left;
          padding: 0 0 6px 10px;
          border-left: 10px solid #A6D8CB;
          border-bottom: 2px solid #A6D8CB;
        }
      </style>
 </head>
 <body>
 <div class="d9"><h3>XO GamE</h3></div>
 <p>
 <a href=${picture} style="position: relative;  left: 1000px;">
 <img border="0" alt="W3Schools" src="logo_w3s.gif" width="100" height="100">
 </a>
 <p style="position: relative;left: 1000px;">${name}</p>
 <p style="position: relative;left: 1000px;"><h>${email}</p>
 </h><a class="button-1" >New game</a></h>
 <a href= "https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=http://localhost:8080/login"
        class="button-2" >log out Google</a>

</body>
</html>
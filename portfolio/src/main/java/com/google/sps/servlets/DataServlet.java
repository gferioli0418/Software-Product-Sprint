// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;
import java.io.*; 
import java.util.*;
import java.io.IOException;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class Message{
  private String name;
  private String email;
  private String number;
  private String subject;
  private String message;

  Message() {

  }

  Message(String name, String email, String number, String subject, String message) {
    this.name = name;
    this.email = email;
    this.number = number;
    this.subject = subject;
    this.message = message;
  }
}

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  ArrayList<String> messageArr = new ArrayList<String>(1);

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
  
    //  response.setContentType("application/json;");

    // ArrayList<String> strArr = new ArrayList<String>(3);
    // strArr.add("Giovanni Ferioli");
    // strArr.add("Jose Romero");
    // strArr.add("Daniela Hernandez");
    // String json = new Gson().toJson(strArr);

    // response.getWriter().println(json);

  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    String name = getParameter(request, "name", "");
    String email = getParameter(request, "email", "");
    String number = getParameter(request, "number", "");
    String subject = getParameter(request, "subject", "");
    String message = getParameter(request, "message", "");

    Message mes = new Message(name, email, number, subject, message);

    String json = new Gson().toJson(mes);
    messageArr.add(json);

    // Respond with the result.
    response.setContentType("text/html;");
    response.getWriter().println(messageArr);
    response.sendRedirect("/index.html");
  } 

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}

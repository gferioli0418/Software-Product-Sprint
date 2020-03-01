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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.io.*; 
import java.util.*;
import java.io.IOException;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class Message{
  private long id;
  private String name;
  private String email;
  private String number;
  private String subject;
  private String message;
  private long timeStamp;

  Message() {
      
  }

  Message(Long ID,String name, String email, String number, String subject, String message, long time) {
    this.id =ID;
    this.name = name;
    this.email = email;
    this.number = number;
    this.subject = subject;
    this.message = message;
    this.timeStamp = time;
  }
}

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      
    Query query = new Query("Message").addSort("timestamp", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<Message> messages = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      long id = entity.getKey().getId();
      String title = (String) entity.getProperty("title");
      long timestamp = (long) entity.getProperty("timestamp");

      String name = (String) entity.getProperty("name");
      String email = (String) entity.getProperty("email");
      String number = (String) entity.getProperty("number");
      String subject = (String) entity.getProperty("subject");
      String message = (String) entity.getProperty("message");

      Message mes = new Message(id ,name, email, number, subject, message, timestamp);
      messages.add(mes);
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(messages));
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    String name = getParameter(request, "name", "");
    String email = getParameter(request, "email", "");
    String number = getParameter(request, "number", "");
    String subject = getParameter(request, "subject", "");
    String message = getParameter(request, "message", "");
    long timestamp = System.currentTimeMillis();

    Entity mes = new Entity("Message");
    mes.setProperty("name", name);
    mes.setProperty("email", email);
    mes.setProperty("number", number);
    mes.setProperty("subject", subject);
    mes.setProperty("message", message);
    mes.setProperty("timestamp", timestamp);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(mes);
    String json = new Gson().toJson(mes);

    // Respond with the result.
    response.setContentType("text/html;");
    response.getWriter().println(json);
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

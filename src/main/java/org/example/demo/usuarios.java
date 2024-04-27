package org.example.demo;

public class usuarios {
  private String user;
  private String password;
  private String idUser;

  public usuarios(String user, String password, String idUser) {
    this.user = user;
    this.idUser = idUser;
    this.password = password;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getIdUser() {
    return idUser;
  }

  public void setIdUser(String idUser) {
    this.idUser = idUser;
  }
}

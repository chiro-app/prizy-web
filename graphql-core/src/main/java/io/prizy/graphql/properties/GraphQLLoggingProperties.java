package io.prizy.graphql.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Nidhal Dogga
 * @created 08/09/2022 15:17
 */


@ConfigurationProperties("prizy.graphql.logging")
public class GraphQLLoggingProperties {

  private String scheme;
  private String host;
  private Integer port;
  private String username;
  private String password;
  private String index;

  public String getScheme() {
    return scheme;
  }

  public void setScheme(String scheme) {
    this.scheme = scheme;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getIndex() {
    return index;
  }

  public void setIndex(String index) {
    this.index = index;
  }
}

package co.edu.unbosque.wsrestpinkart.dtos;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

public class Usuario {
    @CsvBindByName
    private String username;
    @CsvBindByName
    private String password;
    @CsvBindByName
    private String role;
    @CsvBindByName
    private String coins;

    public Usuario (){

    }
    public Usuario(String username, String password, String role, String coins) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.coins = coins;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    @Override
    public String toString() {
        return "User{" + "username = " + username + ", password = " + password + ", role =" + role + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario user = (Usuario) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, role);
    }
}

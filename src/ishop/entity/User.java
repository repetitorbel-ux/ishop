package ishop.entity;

import ishop.constants.Role;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
/*
Пример набора полей для User: id, login, password, firstname, lastname, дата рождения (LocalDate), роль (ADMIN, CLIENT). Роль делаете с помощью ENUM
 */
public class User implements Serializable {
    private int id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;
    private String birthday;
    private Role role;

    //Конструктор


    public User(int id, String login, String password, String firstname, String lastname, String birthday, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return id == user.id && Objects.equals(login, user.login) && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && Objects.equals(birthday, user.birthday) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, firstname, lastname, birthday, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthday='" + birthday + '\'' +
                ", role=" + role +
                '}';
    }

    //Геттеры
    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getBirthday() {
        return birthday;
    }


    public Role getRole() {
        return role;
    }

    //Сеттеры
    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

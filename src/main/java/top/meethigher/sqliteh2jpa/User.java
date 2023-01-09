package top.meethigher.sqliteh2jpa;

import javax.persistence.*;

/**
 * 用户信息
 *
 * @author chenchuancheng github.com/meethigher
 * @since 2022/12/4 08:56
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {
        "userName", "userPassword"
})})
public class User {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String userName;

    private String userPassword;

    private String userEmail;

    private boolean admin;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}

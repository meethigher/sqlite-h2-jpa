package top.meethigher.sqliteh2jpa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.meethigher.sqliteh2jpa.entity.h2.H2;
import top.meethigher.sqliteh2jpa.entity.h2.repository.H2Repository;
import top.meethigher.sqliteh2jpa.entity.sqlite.SQLite;
import top.meethigher.sqliteh2jpa.entity.sqlite.repository.SQLiteRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenchuancheng github.com/meethigher
 * @since 2023/1/16 00:18
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private SQLiteRepository sqLiteRepository;

    @Resource
    private H2Repository h2Repository;

    @GetMapping("/sqlite")
    public List<SQLite> sqlite() {
        sqLiteRepository.deleteAll();
        SQLite sqLite = new SQLite();
        sqLite.setUserName("sqlite");
        sqLite.setUserPassword("sqlite");
        sqLite.setUserEmail("sqlite");
        sqLite.setAdmin(false);
        sqLiteRepository.save(sqLite);
        return sqLiteRepository.findAll();
    }

    @GetMapping("/h2")
    public List<H2> h2() {
        h2Repository.deleteAll();
        H2 h2 = new H2();
        h2.setUserName("h2");
        h2.setUserPassword("h2");
        h2.setUserEmail("h2");
        h2.setAdmin(false);
        h2Repository.save(h2);
        return h2Repository.findAll();
    }
}

package com.example.demo.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@RequestMapping("${openapi.simpleInventory.base-path:/BulkUpload/1.0.0}")
public class UsersApiController implements UsersApi {

    private final UsersApiDelegate delegate;

    public UsersApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) UsersApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new UsersApiDelegate() {});
    }

    @Override
    public UsersApiDelegate getDelegate() {
        return delegate;
    }

}

package com.example.demo.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@RequestMapping("${openapi.simpleInventory.base-path:/BulkUpload/1.0.0}")
public class StatusidApiController implements StatusidApi {

    private final StatusidApiDelegate delegate;

    public StatusidApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) StatusidApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new StatusidApiDelegate() {});
    }

    @Override
    public StatusidApiDelegate getDelegate() {
        return delegate;
    }

}

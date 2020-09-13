package com.example.demo.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@RequestMapping("${openapi.userBulkUpload.base-path:/BulkUpload/v1}")
public class StatusApiController implements StatusApi {

    private final StatusApiDelegate delegate;

    public StatusApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) StatusApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new StatusApiDelegate() {});
    }

    @Override
    public StatusApiDelegate getDelegate() {
        return delegate;
    }

}

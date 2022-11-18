# OpenFeign 混合调用方式

基于我们多模块架构的设计，我们希望 OpenFeign 可以灵活的走网络调用或者进程内调用。网络调用的部分参照OpenFeign的官方使用文档。下面给出的样例是在有进程内实现时走进城内调用的方法

1. 在声明OpenFeign接口的时候，`FeignClient` 的 `primary` 属性设置为 `false`

```java
@FeignClient(name = "npts-service",
        primary = false,
        contextId = "HybridFeignApi",
        url = "${npts.url:}",
        path = "/npts/api/v1/nptsPtsHeadPre",
        fallback = HybridFeignApiFallback.class)
public interface HybridFeignApi {

    /***
     * 根据核注清单编号和核扣标志查询
     *
     *
     * @param req
     * @param pageReq
     * @return
     */
    @PostMapping("/hello")
    Result<List<OptionDTO>> hello(@RequestBody HelloReq req, @SpringQueryMap PageReq pageReq);
}
```

2. 接口提供方实现前面定义的接口，在 controller 层实现即可， 在实现类上加 `@Primary` 注解即可

```
@Tag(name = "世界真美好")
@RestController
@RequestMapping("v1/demo")
@Primary
public class DemoController implements HybridFeignApi {

    @Override
    public Result<List<OptionDTO>> hello(NptsPtsAttachmentReq req, PageReq pageReq) {
        return Result.of(List.of(OptionDTO.of("world", pageReq.getPage() + "")));
    }
}
```

3. 使用方无任何变化
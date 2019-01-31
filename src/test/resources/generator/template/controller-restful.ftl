package ${webbasePackage}.action;

import com.f.base.BaseAction;
import com.f.vo.ResponseVo;
import ${webbasePackage}.entity.${modelNameUpperCamel};
import ${webbasePackage}.service.${modelNameUpperCamel}Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import java.util.List;

/**
* Created by ${author} on ${date}.
*/
@RestController
@RequestMapping("${baseRequestMapping}")
public class ${modelNameUpperCamel}Action extends BaseAction {
    @Autowired
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

  @GetMapping("/list")
    public ResponseVo list() {
        List list = ${modelNameLowerCamel}Service.queryAll();
        return ResponseVo.builder().data(list).build();
    }

    @PostMapping("/add")
    public ResponseVo save(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.save(${modelNameLowerCamel});
        return ResponseVo.builder().data(${modelNameLowerCamel}).build();
    }

    @PostMapping("/update")
    public ResponseVo update(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
        return ResponseVo.builder().build();
    }

    @DeleteMapping("/{id}")
    public ResponseVo delete(@PathVariable Long id) {
        ${modelNameLowerCamel}Service.deleteById(id);
        return ResponseVo.builder().build();
    }


}

package ${webbasePackage}.service;

import ${webbasePackage}.dao.${modelNameUpperCamel}Dao;
import ${webbasePackage}.entity.${modelNameUpperCamel};
import ${webbasePackage}.service.${modelNameUpperCamel}Service;
import ${basePackage}.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by ${author} on ${date}.
 */
@Service
@Transactional
public class ${modelNameUpperCamel}ServiceImpl extends BaseService<${modelNameUpperCamel}> implements ${modelNameUpperCamel}Service {
    @Resource
    private ${modelNameUpperCamel}Dao ${modelNameLowerCamel}Dao;

}

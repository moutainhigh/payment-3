package ${webbasePackage}.dao;

import ${webbasePackage}.mapper.${modelNameUpperCamel}Mapper;
import ${webbasePackage}.entity.${modelNameUpperCamel};
import ${webbasePackage}.dao.${modelNameUpperCamel}Dao;
import ${basePackage}.core.BaseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by ${author} on ${date}.
 */
@Service
@Transactional
public class ${modelNameUpperCamel}DaoImpl extends BaseDao<${modelNameUpperCamel}> implements ${modelNameUpperCamel}Dao {
    @Resource
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

}

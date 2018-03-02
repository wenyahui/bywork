<#macro mapperEl value>${r"#{"}${value}}</#macro>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${package}.${pathName}.dao.${className}Mapper">

    <resultMap type="${package}.${pathName}.po.${className}" id="BaseResultMap">
	    <#list columns as column>
		     <result property="${column.attrname}" column="${column.columnName}"/>
		</#list>
    </resultMap>
    
    <sql id="Base_Column_List" >
   		<#list columns as column>
   		      ${column.columnName}
   		       <#if column_has_next>
				   ,
			   </#if>
   		</#list>
  	</sql>
    
	<select id="queryObject"  resultMap="BaseResultMap" parameterType="java.lang.Integer">
		select 
		 <include refid="Base_Column_List" /> 
		 from ${tableName} where ${pk.columnName} =  <@mapperEl pk.columnName/>
	</select>

	<select id="queryList" resultMap="BaseResultMap">
		select 
		
		<include refid="Base_Column_List" /> 
		
		from ${tableName}
		
		<choose>
            <when test="sidx != null and sidx.trim() != ''">
                order by <#noparse> ${sidx} ${order} </#noparse>
            </when>
			<otherwise>
                order by ${pk.columnName} desc
			</otherwise>
        </choose>
     	
	</select>
	
 	<select id="queryTotal" parameterType="java.lang.Integer" >
		select count(*) from ${tableName} 
	</select>
	 
	<insert id="save" parameterType="${package}.${pathName}.po.${className}"<#if pk.extra == 'auto_increment'> useGeneratedKeys="true" keyProperty="${pk.attrname}"</#if>>
		 insert into ${tableName}
		 <trim prefix="(" suffix=")" suffixOverrides="," >
		    <#list columns as column>
		        <if test="${column.columnName} != null" >
				  <#if column.columnName != pk.columnName || pk.extra != 'auto_increment'>
					  `${column.columnName}`
					   <#if column_has_next>
					   ,
					   </#if>
				  </#if>
				</if>
			</#list>
		 </trim>
		 <trim prefix="values (" suffix=")" suffixOverrides="," >
			<#list columns as column>
				<if test=" ${column.columnName} != null" >
					<#if column.columnName != pk.columnName || pk.extra != 'auto_increment'>
					   <@mapperEl column.attrname/>
					   <#if column_has_next>
					   ,
					   </#if>
					</#if>
				</if>		
			</#list>
		</trim>
	</insert>
	 
	<update id="update" parameterType="${package}.${pathName}.po.${className}">
		update ${tableName} 
		<set>
			<#list columns as column>
				<#if column.columnName != pk.columnName>
						<if test="${column.attrname} != null">
						  `${column.columnName}` = <@mapperEl column.attrname/>
						  <#if column_has_next>
						  ,
						  </#if>
						 </if>
				 </#if>
			</#list>
		</set>
		where ${pk.columnName} = <@mapperEl pk.attrname/>
	</update>
	
	<delete id="delete">
		delete from ${tableName} where ${pk.columnName} = <@mapperEl pk.columnName/>
	</delete>
	
	<delete id="deleteBatch">
		delete from ${tableName} where ${pk.columnName} in 
		<foreach item="${pk.attrname}" collection="array" open="(" separator="," close=")">
			<@mapperEl pk.attrname/>
		</foreach>
	</delete>

</mapper>
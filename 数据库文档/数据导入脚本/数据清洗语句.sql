-- 甲方公司提供的mysql数据库连接信息
 abc-polardb-01.rwlb.rds.aliyuncs.com
 qixin_data_rw_pe
 qAgMCIHzUuWI$
 industry_chain

业务表:
        `1.company_information公司信息表`,
        `2.main_product主营产品表`,
        `2.up_down_product上下游产品表`,
        `3.product_index产品折线图`,
        `4.company_profile公司简介`,
        `5.upstream_downstream_industries上下游关联主营产品`,
        `6.revenue_situation公司近4年的营收金额、占比`,
        `7.product information产品信息`

-- 连接到甲方数据库后，将业务表拷贝到本地数据库，在本地数据库执行下面mysql语句

-- 第1步：创建公司表备份，公司id是重点，备份是为了确保公司的id不能变
create table `company_bak` as select * from `company`;

`tmp_公司基本资料` 和 `tmp_上市公司对应的股票基本资料数据`
这两张表的数据是从公司基本资料.xls表格导入的, 在执行下面语句之前, 先清空这两张表的数据,
然后把公司基本资料.xls数据导入到`tmp_公司基本资料` 和 `tmp_上市公司对应的股票基本资料数据`(根据字段注释名来对应导入)

drop table if exists `tmp_company临时公司表`;
drop table if exists `company`;
drop table if exists `temp_上市公司股票代码临时表`;
drop table if exists `tmp_company_info`;
drop table if exists `product`;

-- 创建 tmp_company临时公司表
CREATE TABLE if not exists `tmp_company临时公司表` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ticker_symbol` varchar(50)  DEFAULT NULL COMMENT '股票代码',
  `stk_name` varchar(100)  DEFAULT NULL COMMENT '股票简称',
  `org` varchar(300)  DEFAULT NULL COMMENT '公司全称',
  `name` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `com_sname` varchar(255) DEFAULT NULL COMMENT '公司简称',
  `create_entry` varchar(1) NOT NULL DEFAULT '0' COMMENT '词条创建状态 0-未创建  1-已创建',
  `audit_status` varchar(1) NOT NULL DEFAULT '0' COMMENT '公司审核状态 0-待审核  1-审核通过 2-审核未通过',
  `com_name_en` varchar(255) DEFAULT NULL COMMENT '英文名称',
  `com_sname_en` varchar(255) DEFAULT NULL COMMENT '英文名称缩写',
  `country` varchar(255) DEFAULT NULL COMMENT '国籍',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `city` varchar(255) DEFAULT NULL COMMENT '城市',
  `usc_code` varchar(255) DEFAULT NULL COMMENT '统一社会信用代码',
  `reg_ass` varchar(255) DEFAULT NULL COMMENT '注册资本（万元）',
  `cur_name` varchar(255) DEFAULT NULL COMMENT '货币名称',
  `registration_place` varchar(255) DEFAULT NULL COMMENT '注册地址',
  `off_add` varchar(255) DEFAULT NULL COMMENT '办公地址',
  `zip_code` varchar(255) DEFAULT NULL COMMENT '邮编',
  `tel` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `fax` varchar(255) DEFAULT NULL COMMENT '联系传真',
  `email` varchar(255) DEFAULT NULL COMMENT '电子信箱',
  `web` varchar(255) DEFAULT NULL COMMENT '公司网址',
  `main_business` text COMMENT '主营业务',
  `ope_scope` text COMMENT '经营范围',
  `introduction` text COMMENT '公司简介',
  `est_date` varchar(255) DEFAULT NULL COMMENT '成立日期',
  `com_end_date` varchar(255) DEFAULT NULL COMMENT '公司终止日期',
  `com_type` varchar(255) DEFAULT NULL COMMENT '公司类型',
  `cominfo_type` varchar(255) DEFAULT NULL COMMENT '公司类型1：1-其他公司，2-金融公司',
  `bus_nature` varchar(255) DEFAULT NULL COMMENT '企业性质',
  `business_introduction` varchar(2000)  DEFAULT NULL COMMENT '公司简介',
  `create_user_id` char(0) NOT NULL DEFAULT '' COMMENT '创建人id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8274 DEFAULT CHARSET=utf8mb4;

-- 创建 公司基本信息临时表
CREATE TABLE if not exists `tmp_company_info` (
  `ticker_symbol` varchar(50)  DEFAULT NULL COMMENT '股票代码',
  `stk_name` varchar(100)  DEFAULT NULL COMMENT '股票简称',
  `org` varchar(300)  DEFAULT NULL COMMENT '公司全称',
  `com_type` varchar(50)  DEFAULT NULL COMMENT '公司类型',
  `business_introduction` varchar(2000)  DEFAULT NULL COMMENT '公司简介',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建 上市公司股票代码临时表
CREATE TABLE if not exists `temp_上市公司股票代码临时表` (
  `ticker_symbol` varchar(50)  DEFAULT NULL COMMENT '股票代码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建 公司表
CREATE TABLE `company` (
  `id` varchar(32)  NOT NULL DEFAULT '0' COMMENT 'id',
  `ticker_symbol` varchar(6)   DEFAULT NULL COMMENT '股票代码',
  `stk_name` varchar(100)   DEFAULT NULL COMMENT '股票简称',
  `org` varchar(300)   DEFAULT NULL COMMENT '公司全称',
  `name` varchar(255)  DEFAULT NULL COMMENT '公司名称',
  `alias_name` varchar(255)  DEFAULT NULL COMMENT '别名（公司简称）',
  `create_entry` varchar(1)  NOT NULL DEFAULT '0' COMMENT '创建状态 0-未创建 1-已创建',
  `audit_status` varchar(1)  NOT NULL DEFAULT '0' COMMENT '审核状态 0-待审核 1-审核通过 2-审核未通过',
  `com_name_en` varchar(255)  DEFAULT NULL COMMENT '英文名称',
  `com_sname_en` varchar(255)  DEFAULT NULL COMMENT '英文名称缩写',
  `country` varchar(255)  DEFAULT NULL COMMENT '国籍',
  `province` varchar(255)  DEFAULT NULL COMMENT '省份',
  `city` varchar(255)  DEFAULT NULL COMMENT '城市',
  `usc_code` varchar(255)  DEFAULT NULL COMMENT '统一社会信用代码',
  `reg_ass` varchar(255)  DEFAULT NULL COMMENT '注册资本（万元）',
  `cur_name` varchar(255)  DEFAULT NULL COMMENT '货币名称',
  `registration_place` varchar(255)  DEFAULT NULL COMMENT '注册地址',
  `off_add` varchar(255)  DEFAULT NULL COMMENT '办公地址',
  `zip_code` varchar(255)  DEFAULT NULL COMMENT '邮编',
  `tel` varchar(255)  DEFAULT NULL COMMENT '联系电话',
  `fax` varchar(255)  DEFAULT NULL COMMENT '联系传真',
  `email` varchar(255)  DEFAULT NULL COMMENT '电子信箱',
  `web` varchar(255)  DEFAULT NULL COMMENT '公司网址',
  `main_business` text  COMMENT '主营业务',
  `scope` text  COMMENT '经营范围',
  `introduction` text  COMMENT '公司简介',
  `est_date` varchar(255)  DEFAULT NULL COMMENT '成立日期',
  `com_end_date` varchar(255)  DEFAULT NULL COMMENT '公司终止日期',
  `com_type` varchar(255)  DEFAULT NULL COMMENT '公司类型',
  `cominfo_type` varchar(255)  DEFAULT NULL COMMENT '公司类型1：1-其他公司，2-金融公司',
  `bus_nature` varchar(255)  DEFAULT NULL COMMENT '企业性质',
  `business_introduction` varchar(2000)   DEFAULT NULL COMMENT '公司简介',
  `create_user_id` varchar(255)  NOT NULL DEFAULT '' COMMENT '创建人id',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `last_update_time` bigint DEFAULT NULL COMMENT '最近更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 创建产品表product
CREATE TABLE `product` (
  `id` varchar(100)   NOT NULL COMMENT '主营产品id',
  `parent_id` varchar(100)   DEFAULT NULL COMMENT '该主营产品的父节点产品id',
  `name` varchar(300)   DEFAULT NULL COMMENT '主营产品名称',
  `create_entry` varchar(1)  NOT NULL DEFAULT '0' COMMENT '是否已经创建词条 1:已创建 0:未创建',
  `industry_status` varchar(1)  NOT NULL DEFAULT '0' COMMENT '产业链创建状态 1:已创建 0:未创建',
  `product_tree_status` varchar(1)  NOT NULL DEFAULT '0',
  `artwork_status` varchar(1)  NOT NULL DEFAULT '0' COMMENT '生产工艺图创建状态 1:已创建 0:未创建',
  `audit_status` varchar(1)  NOT NULL DEFAULT '0' COMMENT '审核状态 0-待审核 1-审核通过 2-审核未通过',
  `type` varchar(1)  NOT NULL DEFAULT '' COMMENT '产品区分类型 1：产品种类 2：产品类型 3：产品单元',
  `create_user_id` varchar(255)  NOT NULL DEFAULT '' COMMENT '创建人id',
  `create_time` bigint NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- 第2步：创建temp_上市公司股票代码临时表
insert into `temp_上市公司股票代码临时表`
select distinct tmp.ticker_symbol
from (
		select ticker_symbol as ticker_symbol 
		from  `1.company_information公司信息表`
		union 
		select secu as ticker_symbol
		from  `2.main_product主营产品表`
		union 
		select trd_code as ticker_symbol
		from  `4.company_profile公司简介`
		union 
		select secu as ticker_symbol
		from  `2.main_product主营产品表`
		union 
		select substring(secu, 1, 6) as ticker_symbol
		from  `6.revenue_situation公司近4年的营收金额、占比`
)tmp

-- 第3步： 根据股票代码 将4.company_profile公司简介 和 6.revenue_situation公司近4年的营收金额、占比 两张表提取出公司基本信息
insert into tmp_company_info
select a.`ticker_symbol`, b.stk_name, d.org, d.com_type, b.business_introduction, b.ctrate_time as create_time, b.last_update_time 
from 
	`temp_上市公司股票代码临时表` a 
left join 
	`4.company_profile公司简介` b
on a.`ticker_symbol` = b.trd_code
left join 
	(select distinct substring(secu,1,6) as secu, com_type, org from `6.revenue_situation公司近4年的营收金额、占比`) d
on a.`ticker_symbol` = d.secu

-- 第4步： 补全公司名称
update tmp_company_info set stk_name = org where stk_name is null; 

update tmp_company_info set org = stk_name where org is null; 

update tmp_company_info set create_time = now() where create_time is null; 

-- 第5步：插入公司表数据
insert into `tmp_company临时公司表` 
(
    ticker_symbol, stk_name, org, name, com_sname, create_entry, audit_status, com_name_en, com_sname_en, country,
    province, city, usc_code, reg_ass, cur_name, registration_place, off_add, zip_code, tel, fax, email, web,
    main_business, ope_scope, introduction, est_date, com_end_date, com_type, cominfo_type, bus_nature,
    business_introduction, create_user_id, create_time, last_update_time
)
select
    e.ticker_symbol as ticker_symbol, e.stk_name, e.org, f.com_name as name, f.com_sname, "1" as create_entry,
    "1" as audit_status, f. com_name_en, f.com_sname_en, f.country, f.province, f.city, f.usc_code, f.reg_ass, f.cur_name,
    f.reg_add as registration_place, f.off_add, f.zip_code, f.tel, f.fax, f.email, f.web, f.main_bus as main_business,
    f.ope_scope, f.com_intro as introduction, f.est_date, f.com_end_date, f.com_type, f.cominfo_type, f.bus_nature,
    e.business_introduction, "" as create_user_id, e.create_time, e.last_update_time
from 
	tmp_company_info e
left join 
	(
		select distinct b.sec_code,a.*
		from `tmp_公司基本资料` a, (select com_uni_code, sec_code from `tmp_上市公司对应的股票基本资料数据`) b
		where a.com_uni_code = b.com_uni_code
    ) f
on e.`ticker_symbol` = f.sec_code


-- 第6步：插入 公司有多个股票代码数据 合并成一个股票代码 如：600843,900924
insert into tmp_company临时公司表
(
    ticker_symbol, stk_name, org, name, com_sname, create_entry, audit_status, com_name_en, com_sname_en, country, province,
    city, usc_code, reg_ass, cur_name, registration_place, off_add, zip_code, tel, fax, email, web, main_business,
    ope_scope, introduction, est_date, com_end_date, com_type, cominfo_type, bus_nature, business_introduction,
    create_user_id, create_time, last_update_time
)
select distinct GROUP_CONCAT(ticker_symbol) as ticker_symbol, stk_name, org, name, com_sname, create_entry, audit_status,
                com_name_en, com_sname_en, country, province, city, usc_code, reg_ass, cur_name, registration_place,
                off_add, zip_code, tel, fax, email, web, main_business, ope_scope, introduction, est_date, com_end_date,
                com_type, cominfo_type, bus_nature, business_introduction, create_user_id, create_time, last_update_time
from tmp_company临时公司表 
where name is not null
group by name 
having count(1) > 1

-- 第7步：删除 公司有多个股票代码数据
delete from tmp_company临时公司表 
where name in (
                select tmp.name
                from (
                        select name
                        from tmp_company临时公司表
                        where name is not null
                        group by name having count(1) > 1
                     )tmp
                )
 and LENGTH(ticker_symbol) = 6
 
 
-- 第8步：删除空数据
delete from `tmp_company临时公司表`
where ticker_symbol is null

-- 第9步：更新名字
update `tmp_company临时公司表`
set name = org
where name is null

-- 第10步：更新股票代码
update `tmp_company临时公司表`
set ticker_symbol = substring(ticker_symbol, 1, 6)

-- 第11步：
insert into company
select  c.id,
        tmp.ticker_symbol,
        tmp.stk_name,
        tmp.org,
        tmp.name,
        tmp.com_sname as alias_name,
        tmp.create_entry,
        tmp.audit_status,
        tmp.com_name_en,
        tmp.com_sname_en,
        tmp.country,
        tmp.province,
        tmp.city,
        tmp.usc_code,
        tmp.reg_ass,
        tmp.cur_name,
        tmp.registration_place,
        tmp.off_add,
        tmp.zip_code,
        tmp.tel,
        tmp.fax,
        tmp.email,
        tmp.web,
        tmp.main_business,
        tmp.ope_scope as scope,
        tmp.introduction,
        tmp.est_date,
        tmp.com_end_date,
        tmp.com_type,
        tmp.cominfo_type,
        tmp.bus_nature,
        tmp.business_introduction,
        tmp.create_user_id,
        UNIX_TIMESTAMP(tmp.create_time)*1000 as create_time,
        UNIX_TIMESTAMP(tmp.last_update_time)*1000 as last_update_time
from tmp_company临时公司表 tmp, company_bak c
where tmp.ticker_symbol = c.ticker_symbol


insert into company
select distinct
        tmp.id,
        tmp.ticker_symbol,
        tmp.stk_name,
        tmp.org,
        tmp.name,
        tmp.com_sname as alias_name,
        tmp.create_entry,
        tmp.audit_status,
        tmp.com_name_en,
        tmp.com_sname_en,
        tmp.country,
        tmp.province,
        tmp.city,
        tmp.usc_code,
        tmp.reg_ass,
        tmp.cur_name,
        tmp.registration_place,
        tmp.off_add,
        tmp.zip_code,
        tmp.tel,
        tmp.fax,
        tmp.email,
        tmp.web,
        tmp.main_business,
        tmp.ope_scope as scope,
        tmp.introduction,
        tmp.est_date,
        tmp.com_end_date,
        tmp.com_type,
        tmp.cominfo_type,
        tmp.bus_nature,
        tmp.business_introduction,
        tmp.create_user_id,
        UNIX_TIMESTAMP(tmp.create_time)*1000 as create_time,
        UNIX_TIMESTAMP(tmp.last_update_time)*1000 as last_update_time
from
    (
        select t.*, c.id as companyId
        from tmp_company临时公司表 t, company_bak c
        where t.ticker_symbol = c.ticker_symbol
    )tmp
where tmp.companyId is null


-- company表数据导出company.csv文件
-- Neo4j 创建公司节点
load csv with headers from "file:///company.csv" as line
merge (:CompanyEntry {
                        companyEntryId:line.id,
                        tickerSymbol:line.ticker_symbol,
                        stkName:line.stk_name,
                        name:line.name,
                        aliasName:line.alias_name,
                        comNameEn:line.com_name_en,
                        comSnameEn:line.com_sname_en,
                        country:line.country,
                        province:line.province,
                        city:line.city,
                        uscCode:line.usc_code,
                        regAss:line.reg_ass,
                        curName:line.cur_name,
                        registrationPlace:line.registration_place,
                        offAdd:line.off_add,
                        zipCode:line.zip_code,
                        tel:line.tel,
                        fax:line.fax,
                        email:line.email,
                        web:line.web,
                        mainBusiness:line.main_business,
                        scope:line.scope,
                        introduction:line.introduction,
                        estDate:line.est_date,
                        comEndDate:line.com_end_date,
                        comType:line.com_type,
                        cominfoType:line.cominfo_type,
                        busNature:line.bus_nature,
                        businessIntroduction:line.business_introduction,
                        createUserId:line.create_user_id,
                        createTime:toInt(line.create_time),
                        modifyTime:toInt(line.last_update_time)})

==================================================================================================================

-- 插入产品数据
insert into product
select code as id,
       parent as parent_id,
       name,
       "1" as create_entry,
       "0" as industry_status,
	   "0" as product_tree_status,
	   "0" as artwork_status,
	   "1" as audit_status,
	   "1" as type,
	   "" as create_user_id,
	   UNIX_TIMESTAMP(ctrate_time)*1000 as create_time
from `7.product information产品信息`

-- Neo4j 创建产品节点
load csv with headers from "file:///product.csv" as line
merge (:ProductEntry {
                        productEntryId:line.id,
                        name:line.name,
                        type:line.type,
                        createUserId:line.create_user_id,
                        createTime:toInt(line.create_time)
                        })
	
==================================================================================================================
-- 创建Neo4j节点生产关系
-- 生产关系 companyEntryId  公司词条id， productEntryId 产品词条id， 
-- productIncome 公司最新年报在该产品上的营收， incomeOrginRate 公司最新年报在该产品上营收占当年总营收比例， 
-- rate 公司在该年报日该产品的营收占比， avgIncresement 表示以node_id为主营产品的所有上市公司近一月的平均涨幅
select tt.* 
from
(
	select 'companyEntryId' as companyEntryId, 'productEntryId' as productEntryId, 'productIncome' as productIncome, 'incomeOrginRate' as incomeOrginRate, 'rate' as rate, 'avgIncresement' as avgIncresement, 'orderby' as orderby
	union 
	select 
		companyEntryId, 
		productEntryId, 
		productIncome, 
		concat(round(incomeOrginRate*100,2),'%') as incomeOrginRate, 
		concat(round(rate*100,2),'%') as rate, 
		concat(round(avgIncresement*100,2),'%') as avgIncresement,
		'' as orderby
	from
	(
		select 
			tmp1.companyEntryId as companyEntryId, tmp2.companyEntryId as tmp2CompanyEntryId,tmp1.productEntryId as productEntryId,tmp2.productEntryId as tmp2ProductEntryId, 
			tmp1.productIncome, tmp1.incomeOrginRate, tmp2.rate, tmp2.avgIncresement
		from 
		(
			select distinct
						 a.id as companyEntryId, 
						 b.product_code as productEntryId,
						 (case when b.product_income is null then 0 else b.product_income end) as productIncome, -- 公司最新年报在该产品上的营收11, 
						 (case when b.income_orgin_rate is null then 0 else b.income_orgin_rate end) as incomeOrginRate -- 公司最新年报在该产品上营收占当年总营收比例		 			 
			from `tmp_company临时公司表` a left join `1.company_information公司信息表` b
			on a.ticker_symbol = b.ticker_symbol
		)tmp1
		left join 
		(
			select distinct
						 a.id as companyEntryId, 
						 c.node_id as productEntryId,
						 (case when c.rate is null then 0 else c.rate end) as rate, -- 公司在该年报日该产品的营收占比,
						 (case when c.avg_incresement is null then 0 else c.avg_incresement end) as avgIncresement -- 表示以node_id为主营产品的所有上市公司近一月的平均涨幅
			from `tmp_company临时公司表` a left join `2.main_product主营产品表` c
			on  a.ticker_symbol = c.secu 
		)tmp2
		on tmp1.companyEntryId = tmp2.companyEntryId and tmp1.productEntryId = tmp2.productEntryId
	)tmp
	where tmp.productEntryId is not null and tmp.productEntryId is not null 
)tt
order by tt.orderby desc
INTO OUTFILE 'D:\Production.csv' FIELDS TERMINATED by ',' OPTIONALLY ENCLOSED by '"' LINES TERMINATED by '\r\n';

-- 将D盘的Production.csv 文件拷贝到服务器上neo4j的import目录

-- 在Neo4j浏览器客户端执行以下命令 创建公司和产品生产关系
load csv with headers from "file:///Production.csv" as line
match (company:CompanyEntry {companyEntryId:line.companyEntryId}),(productry:ProductEntry{productEntryId:line.productEntryId})
merge (company)-[r:Production {companyEntryId:line.companyEntryId, productEntryId:line.productEntryId, productIncome:line.productIncome,incomeOrginRate:line.incomeOrginRate, rate:line.rate, avgIncresement:line.avgIncresement}]->(productry)
	
-- 设置生产关系的UUID	
MATCH p=()-[r:Production]->() 
set r.uuid = toString(r.uuid)			


==================================================================================================================


-- 产品 生产原材料 关系（`2.up_down_product上下游产品表` 和 `5.upstream_downstream_industries上下游关联主营产品`）
select tt.* 
from
(
	select 'nodeId' as nodeId, 'neighId' as neighId, 'orderby' as orderby
	union 
	select distinct 
		tmp.nodeId,
		tmp.neighId,
		'' as orderby
	from
	(
		select distinct 
			node_id as nodeId, -- 当前产品节点id
			neigh_id as neighId -- , -- 生产原料产品id
			-- avg_incresement as avgIncresement, -- 以生产原料产品为主营产品的所有上市公司近一月的平均涨幅
			-- ctrate_time as createTime -- 生产原料关系创建时间
		from	`2.up_down_product上下游产品表`
		where edge_name = "生产原料" and edge_direction = "up"
		union
		select distinct 
			primary_code as nodeId, 
			related_code as neighId
		from `5.upstream_downstream_industries上下游关联主营产品`
		where edge_type = "生产原料" and edge_direction = "up"
	)tmp
)tt
order by tt.orderby desc
INTO OUTFILE 'D:\Materials.csv' FIELDS TERMINATED by ',' OPTIONALLY ENCLOSED by '"' LINES TERMINATED by '\r\n';

-- 将D盘的Materials.csv 文件拷贝到服务器上neo4j的import目录

-- 在Neo4j浏览器客户端执行以下命令 创建生产原材料关系
load csv with headers from "file:///Materials.csv" as line
match (neighNode:ProductEntry {productEntryId:line.neighId}), (node:ProductEntry{productEntryId:line.nodeId})
merge (neighNode)-[r:Materials]->(node)

-- 设置生产原材料关系的UUID
MATCH p=()-[r:Materials]->() 
set r.uuid = toString(r.uuid)


==================================================================================================================


-- 产品 生产设备 关系
select tt.* 
from
(
	select 'nodeId' as nodeId, 'neighId' as neighId, 'orderby' as orderby
	union 
	select distinct 
		tmp.nodeId,
		tmp.neighId,
		'' as orderby
	from 
	(
		select distinct 
			node_id as nodeId, -- 当前产品节点id
			neigh_id as neighId -- , -- 生产原料产品id
			-- avg_incresement as avgIncresement, -- 以生产原料产品为主营产品的所有上市公司近一月的平均涨幅
			-- ctrate_time as createTime -- 生产原料关系创建时间
		from	`2.up_down_product上下游产品表`
		where edge_name = "生产设备" and edge_direction = "up"
		union
		select distinct 
			primary_code as nodeId, 
			related_code as neighId
		from `5.upstream_downstream_industries上下游关联主营产品`
		where edge_type = "生产设备" and edge_direction = "up"
	)tmp
)tt
order by tt.orderby desc
INTO OUTFILE 'D:\Manufacture.csv' FIELDS TERMINATED by ',' OPTIONALLY ENCLOSED by '"' LINES TERMINATED by '\r\n';

-- 将D盘的Manufacture.csv 文件拷贝到服务器上neo4j的import目录

-- 在Neo4j浏览器客户端执行以下命令 创建生产设备关系
load csv with headers from "file:///Manufacture.csv" as line
match (neighNode:ProductEntry {productEntryId:line.neighId}), (node:ProductEntry{productEntryId:line.nodeId})
merge (neighNode)-[r:Manufacture]->(node)

-- 设置生产设备关系的UUID
MATCH p=()-[r:Manufacture]->() 
set r.uuid = toString(r.uuid)

==================================================================================================================

-- 公司财报季度信息
select tt.* from 
(
	select 'companyEntryId' as companyEntryId, 'productEntryId' as productEntryId, 'tickerSymbol' as tickerSymbol, 'comType' as comType, 'quarter' as quarter, 'productIncome' as productIncome, 'orderby' as orderby
	union
	select distinct
		companyEntryId, -- 公司id
		productEntryId, -- 产品id
		tickerSymbol,   -- 股票代码
		comType,        -- 公司类型
		quarter,        -- 财报季度
		productIncome,   -- 主营产品当期营收金额
		'' as orderby
	from
		(
			select id as companyEntryId, 
						 ticker_symbol as tickerSymbol
			from company
		)tmp1
	left join
		(
			select distinct
				product_id as productEntryId, -- 产品id
				substring(secu,1,6) as secu, -- 股票代码
				com_type as comType, -- 公司类型
				quarter, -- 财报季度
				product_income as productIncome -- 主营产品当期营收金额
				-- product_income_ratio as productIncomeRatio, -- 主营产品当期营收金额占当期总营收金额的比重
			from `6.revenue_situation公司近4年的营收金额、占比`
			where secu is not null
		)tmp2
	on tmp1.tickerSymbol = tmp2.secu
)tt
order by tt.orderby desc
INTO OUTFILE 'D:\CompanyReport.csv' FIELDS TERMINATED by ',' OPTIONALLY ENCLOSED by '"' LINES TERMINATED by '\r\n'





-- 将D盘的CompanyReport.csv 文件拷贝到服务器上neo4j的import目录

-- 在Neo4j浏览器客户端执行以下命令 创建公司财报季度信息节点
load csv with headers from "file:///公司财报季度信息.csv" as line
create (:CompanyReport {companyEntryId:line.companyEntryId, productEntryId:line.id, comType:line.comType, quarter:line.quarter, productIncome:line.productIncome})

=================================================================================================================



-- 
-- load csv with headers from "file:///42.csv" as line
-- match (company:CompanyEntry {companyEntryId:line.companyEntryId})-[r:Production]->(productry:ProductEntry{productEntryId:line.productEntryId})
-- set r.companyEntryId = line.companyEntryId, r.productEntryId = line.productEntryId, r.productIncome = line.productIncome, 
--     r.incomeOrginRate = line.incomeOrginRate, r.rate = line.rate, r.avgIncresement = line.avgIncresement































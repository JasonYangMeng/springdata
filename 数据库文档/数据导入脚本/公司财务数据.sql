-- 财务数据

with tmp_SEC_MAIN_FIN_INDICATORS as (
		select 
				distinct
				`com_uni_code` as 公司统一编码,
				`end_date` as 截止日期,
				com_name as 公司名称,
				sec_name as 证券简称,
				sec_code as 证券代码,
				total_operate_revenue as 营业总收入,
				operate_profit as 营业利润,
				total_profit as 利润总额,
				parent_np as 归属母公司股东的净利润,
				total_non_recur_profit as 非经常性损益,
				cut_nonrecurring_parent_np as 扣非后归属母公司股东的净利润,
				rd_expenses as 研发支出
		from SEC_MAIN_FIN_INDICATORS
		where `statement_type` = "1501001" and end_date > '2018-05-07'
),
tmp_SEC_INCOME_GENERAL as (
		select distinct 
				`com_uni_code` as 公司统一编码,
				`end_date` as 截止日期,
				com_name as 公司名称,
				sec_name as 证券简称,
				sec_code as 证券代码,
				overall_cost as 营业总成本
		from SEC_INCOME_GENERAL
		where `statement_type` = "1501001" and end_date > '2018-05-07'
),
tmp_SEC_FIN_INDI_DERIVATIVE as (
	select distinct 
			`com_uni_code` as 公司统一编码,
			`end_date` as 截止日期,
			com_name as 公司名称,
			sec_name as 证券简称,
			sec_code as 证券代码,
			ebit,
			ebitda
	from SEC_FIN_INDI_DERIVATIVE
	where `statement_type` = "1501001" and end_date > '2018-05-07'
)

select distinct
				a.公司统一编码,
				a.截止日期,
				a.公司名称,
				a.证券简称,
				a.证券代码,
				a.营业总收入,
				a.营业利润,
				a.利润总额,
				a.归属母公司股东的净利润,
				a.非经常性损益,
				a.扣非后归属母公司股东的净利润,
				a.研发支出,
				b.营业总成本,
				c.ebit,
				c.ebitda
from tmp_SEC_MAIN_FIN_INDICATORS a, tmp_SEC_INCOME_GENERAL b, tmp_SEC_FIN_INDI_DERIVATIVE c
where a.公司统一编码 = b.公司统一编码 and a.公司统一编码 = c.公司统一编码
			and a.截止日期 = b.截止日期 and a.截止日期 = c.截止日期


以文本形式导出查询结果(注意: 日期格式注意要选择YMD,日期分隔符为-,零补充日期选择是)
进入数据库:
            ip:172.17.8.1
            端口:3306
            用户名:myang
            密码:Abcft@2021!
========================================================================================================================

进入后执行以下语句

drop table if exists `公司财务数据`

CREATE TABLE `公司财务数据` (
  `公司统一编码` varchar(255) DEFAULT NULL,
  `截止日期` varchar(255) DEFAULT NULL,
  `公司名称` varchar(255) DEFAULT NULL,
  `证券简称` varchar(255) DEFAULT NULL,
  `证券代码` varchar(255) DEFAULT NULL,
  `营业总收入` varchar(255) DEFAULT NULL,
  `营业利润` varchar(255) DEFAULT NULL,
  `利润总额` varchar(255) DEFAULT NULL,
  `归属母公司股东的净利润` varchar(255) DEFAULT NULL,
  `非经常性损益` varchar(255) DEFAULT NULL,
  `扣非后归属母公司股东的净利润` varchar(255) DEFAULT NULL,
  `研发支出` varchar(255) DEFAULT NULL,
  `营业总成本` varchar(255) DEFAULT NULL,
  `ebit` varchar(255) DEFAULT NULL,
  `ebitda` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

再将文本中的数据导入到`公司财务数据`表中

drop table if exists  `company_financial_data`;

create table `company_financial_data`
as
select
		a.id as company_id,
		(case when b.截止日期 is null then now() else b.截止日期 end) as end_date,
		(case when b.营业总收入 is null then '0' else round(b.营业总收入) end) as total_operating_revenue,
		(case when b.营业总成本 is null then '0' else round(b.营业总成本) end) as overall_cost,
		(case when b.营业利润 is null then '0' else round(b.营业利润) end) as operating_profit,
		(case when b.利润总额 is null then '0' else round(b.利润总额) end) as total_profit,
		(case when b.归属母公司股东的净利润 is null then '0' else round(b.归属母公司股东的净利润) end) as parent_np,
		(case when b.非经常性损益 is null then '0' else round(b.非经常性损益) end) as total_non_recur_profit,
		(case when b.扣非后归属母公司股东的净利润 is null then '0' else round(b.扣非后归属母公司股东的净利润) end) as cut_nonrecurring_parent_np,
		(case when b.研发支出 is null then '0' else round(b.研发支出) end) as rd_expenses,
		a.name as name
from company a, `公司财务数据` b
where a.name = b.`公司名称`


将 `company_financial_data` 表中数据以csv文件导出(注意: 不要将列的标题导出)
文件名为company_financial_data.csv,
将该文件拷贝到服务器Neo4j的import文件夹下

==================================================================================================

在Neo4j浏览器客户端中执行下面语句:

load csv from "file:///company_financial_data.csv" as line
merge (:FinanceData {companyEntryId:line[0],
                           endDate:line[1],
                           totalOperatingRevenue:line[2],
                           overallCost:line[3],
                           operatingProfit:line[4],
                           totalProfit:line[5],
                           parentNp:line[6],
                           totalNonRecurProfit:line[7],
                           cutNonrecurringParentNp:line[8],
                           rdExpenses:line[9],
                           name:line[10]
                           })
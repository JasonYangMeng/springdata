-- 公司资产负债
select distinct
	com_uni_code as 公司统一编码,
	com_name as 公司名称,
	sec_name as 证券简称,
	end_date as 截止日期,
	total_current_asset as 流动资产,
	tot_fix_asset as 固定资产,
	long_equity_investment as 长期股权投资,
	total_asset as 资产总计,
	total_current_liabilities as 流动负债,
	total_non_current_liabilities as 非流动负债,
	total_liabilities as 负债合计,
	total_account_equity as 股东权益,
	total_account_parent_equity as 归属母公司股东权益
from SEC_BALANCE_GENERAL
where `statement_type` = "1501001" and end_date > '2018-05-07'

以文本形式导出查询结果(注意: 日期格式注意要选择YMD,日期分隔符为-,零补充日期选择是)

进入数据库:
            ip:172.17.8.1
            端口:3306
            用户名:myang
            密码:Abcft@2021!

进入后, 执行以下语句

drop table if exists `公司资产负债数据`;

CREATE TABLE `公司资产负债数据` (
  `公司统一编码` varchar(255) DEFAULT NULL,
  `公司名称` varchar(255) DEFAULT NULL,
  `证券简称` varchar(255) DEFAULT NULL,
  `截止日期` varchar(255) DEFAULT NULL,
  `流动资产` varchar(255) DEFAULT NULL,
  `固定资产` varchar(255) DEFAULT NULL,
  `长期股权投资` varchar(255) DEFAULT NULL,
  `资产总计` varchar(255) DEFAULT NULL,
  `流动负债` varchar(255) DEFAULT NULL,
  `非流动负债` varchar(255) DEFAULT NULL,
  `负债合计` varchar(255) DEFAULT NULL,
  `股东权益` varchar(255) DEFAULT NULL,
  `归属母公司股东权益` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


再将文本中的数据导入到`公司资产负债数据`表中

drop table if exists `company_balance_sheet_data`;

create table `company_balance_sheet_data`
as
select
		a.id as company_id,
		(case when b.截止日期 is null then now() else b.截止日期 end) as end_date,
		(case when b.流动资产 is null then '0' else round(b.流动资产) end) as total_current_asset,
		(case when b.固定资产 is null then '0' else round(b.固定资产) end) as tot_fix_asset,
		(case when b.长期股权投资 is null then '0' else round(b.长期股权投资) end) as long_equity_investment,
		(case when b.资产总计 is null then '0' else round(b.资产总计) end) as total_asset,
		(case when b.流动负债 is null then '0' else round(b.流动负债) end) as total_current_liabilities,
		(case when b.非流动负债 is null then '0' else round(b.非流动负债) end) as total_non_current_liabilities,
		(case when b.负债合计 is null then '0' else round(b.负债合计) end) as total_liabilities,
		(case when b.股东权益 is null then '0' else round(b.股东权益) end) as total_account_equity,
		(case when b.归属母公司股东权益 is null then '0' else round(b.归属母公司股东权益) end) as total_account_parent_equity,
		a.name as name
from company a, `公司资产负债数据` b
where a.name = b.`公司名称`


将`company_balance_sheet_data`表中数据以csv文件导出(注意: 不要将列的标题导出),
文件名为company_balance_sheet_data.csv,
将该文件拷贝到服务器Neo4j的import文件夹下

========================================================================================================================

在Neo4j浏览器客户端中执行下面语句:

load csv from "file:///company_balance_sheet_data.csv" as line
merge (:AssetsLiabilities {companyEntryId:line[0],
                           endDate:line[1],
                           totalCurrentAsset:line[2],
                           totFixAsset:line[3],
                           longEquityInvestment:line[4],
                           totalAsset:line[5],
                           totalCurrentLiabilities:line[6],
                           totalNonCurrentLiabilities:line[7],
                           totalLiabilities:line[8],
                           totalAccountEquity:line[9],
                           totalAccountParentEquity:line[10],
                           name:line[11]
                           })
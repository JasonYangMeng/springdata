数据库信息
ip:47.111.250.233
端口:13306
用户名:other_abcft
密码:FXmO9WtX5csHVYlo
========================================================================================================================
select distinct
	com_uni_code as 公司统一编码,
	com_name as 公司名称,
	sec_name as 证券简称,
	end_date as 截止日期,
	sale_cash as 销售商品提供劳务收到的现金,
	bussiness_cash_total as 经营活动现金净流量,
	subs_pay_cash as 购建固定无形长期资产支付的现金,
	invest_pay_cash as 投资支付的现金,
	invest_cash_netvalue as 投资活动现金流量,
	rec_invest_reccash as 吸收投资收到的现金,
	rec_borrow_cash as 取得借款收到的现金,
	borrow_cash_netvalue as 筹资活动现金净流量,
	cash_to_netadd as 现金净增加额,
	last_cash as 期末现金余额,
	'' as 折旧与摊销
from SEC_CASHFLOW_GENERAL
where `statement_type` = "1501001" and end_date > date_sub(now(), interval 3 year)

========================================================================================================================
以文本形式导出查询结果(注意: 日期格式注意要选择YMD,日期分隔符为-,零补充日期选择是)

`公司现金流量数据`表所在的数据库为:
                                ip:172.17.8.1
                                端口:3306
                                用户名:myang
                                密码:Abcft@2021!
进入该数据库, 执行以下语句

drop table if exists `公司现金流量数据`;

CREATE TABLE `公司现金流量数据` (
  `公司统一编码` varchar(255) DEFAULT NULL,
  `公司名称` varchar(255) DEFAULT NULL,
  `证券简称` varchar(255) DEFAULT NULL,
  `截止日期` varchar(255) DEFAULT NULL,
  `销售商品提供劳务收到的现金` varchar(255) DEFAULT NULL,
  `经营活动现金净流量` varchar(255) DEFAULT NULL,
  `购建固定无形长期资产支付的现金` varchar(255) DEFAULT NULL,
  `投资支付的现金` varchar(255) DEFAULT NULL,
  `投资活动现金流量` varchar(255) DEFAULT NULL,
  `吸收投资收到的现金` varchar(255) DEFAULT NULL,
  `取得借款收到的现金` varchar(255) DEFAULT NULL,
  `筹资活动现金净流量` varchar(255) DEFAULT NULL,
  `现金净增加额` varchar(255) DEFAULT NULL,
  `期末现金余额` varchar(255) DEFAULT NULL,
  `折旧与摊销` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

再将文本中的数据导入到`公司现金流量数据`表中

drop table if exists `company_cash_flow_data`;

create table `company_cash_flow_data`
as
select
    a.id as company_id,
    (case when b.截止日期 is null then now() else b.截止日期 end) as end_date,
    (case when b.销售商品提供劳务收到的现金 is null then '0' else round(b.销售商品提供劳务收到的现金) end) as sale_cash,
    (case when b.经营活动现金净流量 is null then '0' else round(b.经营活动现金净流量) end) as bussiness_cash_total,
    (case when b.购建固定无形长期资产支付的现金 is null then '0' else round(b.购建固定无形长期资产支付的现金) end) as subs_pay_cash,
    (case when b.投资支付的现金 is null then '0' else round(b.投资支付的现金) end) as invest_pay_cash,
    (case when b.投资活动现金流量 is null then '0' else round(b.投资活动现金流量) end) as invest_cash_netvalue,
    (case when b.吸收投资收到的现金 is null then '0' else round(b.吸收投资收到的现金) end) as rec_invest_reccash,
    (case when b.取得借款收到的现金 is null then '0' else round(b.取得借款收到的现金) end) as rec_borrow_cash,
    (case when b.筹资活动现金净流量 is null then '0' else round(b.筹资活动现金净流量) end) as borrow_cash_netvalue,
    (case when b.现金净增加额 is null then '0' else round(b.现金净增加额) end) as cash_to_netadd,
    (case when b.期末现金余额 is null then '0' else round(b.期末现金余额) end) as last_cash,
    (case when b.折旧与摊销 is null then '0' else round(b.折旧与摊销) end) as deprec_amortiza,
    a.name as name
from company a, `公司现金流量数据` b
where a.name = b.`公司名称`

将`company_cash_flow_data`表中数据以csv文件导出(注意: 不要将列的标题导出),
文件名为company_cash_flow_data.csv,
将该文件拷贝到服务器Neo4j的import文件夹下

========================================================================================================================

在Neo4j浏览器客户端中执行下面语句:

    load csv from "file:///company_cash_flow_data.csv" as line
    merge (:CashFlowData {
                           companyEntryId:line[0],
                           endDate:line[1],
                           saleCash:line[2],
                           businessCashTotal:line[3],
                           subsPayCash:line[4],
                           investPayCash:line[5],
                           investCashNetValue:line[6],
                           recInvestRecCash:line[7],
                           recBorrowCash:line[8],
                           borrowCashNetValue:line[9],
                           cashToNetAdd:line[10],
                           lastCash:line[11],
                           depRecAmortization:line[12],
                           name:line[13]
                          })

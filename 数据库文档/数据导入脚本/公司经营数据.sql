-- 公司经营数据
with tmp_SEC_FIN_INDI_DERIVATIVE as (
	  select distinct
				`com_uni_code` as 公司统一编码,
				com_name as 公司名称,
				sec_name as 证券简称,
				`end_date` as 截止日期,
				`report_type` as 公告类型,
				`statement_type` as 报表类型编码,
				`roa` as 'ROA总资产净利率',
				`return_invest_ratio` as 'ROIC投入资本回报率',
				`gross_profit_margin` as '销售毛利率',
				`net_profit_tot_revenue` as '销售净利润',
				`net_profit_yoyg` as '净利润同比增长',
				`ebit` as 'EBITMARGIN息税前利润',
				`ebitda` as 'EBITDAMARGIN息税折旧摊销前利润',
				`salegoods_labor_revenue` as '销售商品提供劳务收到的现金营业收入'
		from `SEC_FIN_INDI_DERIVATIVE`
		where `statement_type` = "1501001" and end_date > '2018-05-07'
    ),
	tmp_SEC_MAIN_FIN_INDICATORS as (
			select distinct
					com_uni_code as 公司统一编码,
					com_name as 公司名称,
				    sec_name as 证券简称,
					`end_date` as 截止日期,
					`report_type` as 公告类型,
					`statement_type` as 报表类型编码,
					diluted_roe as 'ROE全面摊薄净资产收益率',
					diluted_roe_yoyg as 'ROE全面摊薄净资产收益率同比',
					weighted_roe as 'ROE加权平均净资产收益率',
					cut_nonrecurring_diluted_roe as '扣非后ROE扣除非经常性损益后的全面摊薄净资产收益率',
					parent_asset_liability_ratio as '资产负债率',
					receivable_turnover_ratio as '资产周转率应收账款周转率'
			from `SEC_MAIN_FIN_INDICATORS`
			where statement_type = "1501001" and end_date > '2018-05-07'
)

select distinct
		a.公司统一编码,
		a.公司名称,
		a.证券简称,
		a.截止日期,
		a.公告类型,
		a.报表类型编码,
		a.ROA总资产净利率,
		a.ROIC投入资本回报率,
		a.销售毛利率,
		a.销售净利润,
		a.净利润同比增长,
		a.EBITMARGIN息税前利润,
		a.EBITDAMARGIN息税折旧摊销前利润,
		a.销售商品提供劳务收到的现金营业收入,
		b.ROE全面摊薄净资产收益率,
		b.ROE全面摊薄净资产收益率同比,
		b.ROE加权平均净资产收益率,
		b.扣非后ROE扣除非经常性损益后的全面摊薄净资产收益率,
		b.资产负债率,
		b.资产周转率应收账款周转率
from tmp_SEC_MAIN_FIN_INDICATORS b, tmp_SEC_FIN_INDI_DERIVATIVE a
where a.公司统一编码 = b.公司统一编码
			 and a.截止日期 = b.截止日期  and a.公告类型 = b.公告类型
order by b.公司统一编码

以文本形式导出查询结果(注意: 日期格式注意要选择YMD,日期分隔符为-,零补充日期选择是)
进入数据库为:
            ip:172.17.8.1
            端口:3306
            用户名:myang
            密码:Abcft@2021!
========================================================================================================================

进入后执行以下语句

drop table if exists `公司经营数据`;

CREATE TABLE `公司经营数据` (
  `公司统一编码` varchar(255) DEFAULT NULL,
  `公司名称` varchar(255) DEFAULT NULL,
  `证券简称` varchar(255) DEFAULT NULL,
  `截止日期` varchar(255) DEFAULT NULL,
  `公告类型` varchar(255) DEFAULT NULL,
  `报表类型编码` varchar(255) DEFAULT NULL,
  `ROA总资产净利率` varchar(255) DEFAULT NULL,
  `ROIC投入资本回报率` varchar(255) DEFAULT NULL,
  `销售毛利率` varchar(255) DEFAULT NULL,
  `销售净利润` varchar(255) DEFAULT NULL,
  `净利润同比增长` varchar(255) DEFAULT NULL,
  `EBITMARGIN息税前利润` varchar(255) DEFAULT NULL,
  `EBITDAMARGIN息税折旧摊销前利润` varchar(255) DEFAULT NULL,
  `销售商品提供劳务收到的现金营业收入` varchar(255) DEFAULT NULL,
  `ROE全面摊薄净资产收益率` varchar(255) DEFAULT NULL,
  `ROE全面摊薄净资产收益率同比` varchar(255) DEFAULT NULL,
  `ROE加权平均净资产收益率` varchar(255) DEFAULT NULL,
  `扣非后ROE扣除非经常性损益后的全面摊薄净资产收益率` varchar(255) DEFAULT NULL,
  `资产负债率` varchar(255) DEFAULT NULL,
  `资产周转率应收账款周转率` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

再将文本中的数据导入到`公司经营数据`表中

=====================================================================================

drop table if exists  `company_operating_data`;

create table `company_operating_data`
as
select
		a.id as company_id,
		(case when b.截止日期 is null then now() else b.截止日期 end) as end_date,
		(case when b.ROA总资产净利率 is null then '0' else round(b.ROA总资产净利率, 2) end) as roa,
		(case when b.ROIC投入资本回报率 is null then '0' else round(b.ROIC投入资本回报率, 2) end) as return_invest_ratio,
		(case when b.销售毛利率 is null then '0' else round(b.销售毛利率, 2) end) as gross_profit_margin,
		(case when b.销售净利润 is null then '0' else round(b.销售净利润, 2) end) as net_profit_tot_revenue,
		(case when b.净利润同比增长 is null then '0' else round(b.净利润同比增长, 2) end) as net_profit_tot_revenue_yoy,
		(case when b.EBITMARGIN息税前利润 is null then '0' else round(b.EBITMARGIN息税前利润) end) as ebit,
		(case when b.EBITDAMARGIN息税折旧摊销前利润 is null then '0' else round(b.EBITDAMARGIN息税折旧摊销前利润, 2) end) as ebitda,
		(case when b.销售商品提供劳务收到的现金营业收入 is null then '0' else round(b.销售商品提供劳务收到的现金营业收入, 2) end) as salegoods_labor_revenue,
		(case when b.ROE全面摊薄净资产收益率 is null then '0' else round(b.ROE全面摊薄净资产收益率, 2) end) as diluted_roe,
		(case when b.ROE全面摊薄净资产收益率同比 is null then '0' else round(b.ROE全面摊薄净资产收益率同比, 2) end) as diluted_roe_yoy,
		(case when b.ROE加权平均净资产收益率 is null then '0' else round(b.ROE加权平均净资产收益率, 2) end) as weighted_roe,
		(case when b.扣非后ROE扣除非经常性损益后的全面摊薄净资产收益率 is null then '0' else round(b.扣非后ROE扣除非经常性损益后的全面摊薄净资产收益率, 2) end) as cut_nonrecurring_diluted_roe,
		(case when b.资产负债率 is null then '0' else round(b.资产负债率, 2) end) as parent_asset_liability_ratio,
		(case when b.资产周转率应收账款周转率 is null then '0' else round(b.资产周转率应收账款周转率, 2) end) as receivable_turnover_ratio,
		a.name as name
from company a, `公司经营数据` b
where a.name = b.`公司名称`


将 `company_operating_data` 表中数据以csv文件导出(注意: 不要将列的标题导出)
文件名为company_operating_data.csv,
将该文件拷贝到服务器Neo4j的import文件夹下

===========================================================================================================================

在Neo4j浏览器客户端执行以下语句:

load csv from "file:///company_operating_data.csv" as line
merge (:OperateData {companyEntryId:line[0],
                           endDate:line[1],
                           roa:line[2],
                           returnInvestRatio:line[3],
                           grossProfitMargin:line[4],
                           netProfitTotRevenue:line[5],
                           netProfitTotRevenueYoy:line[6],
                           ebiT:line[7],
                           ebiTda:line[8],
                           saleGoodsLaborRevenue:line[9],
                           dilutedRoe:line[10],
                           dilutedRoeYoy:line[11],
                           weightedRoe:line[12],
                           cutNonrecurringDilutedRoe:line[13],
                           parentAssetLiabilityRatio:line[14],
                           receivableTurnoverRatio:line[15],
                           name:line[16]
                           })
			 
			 
			 
	
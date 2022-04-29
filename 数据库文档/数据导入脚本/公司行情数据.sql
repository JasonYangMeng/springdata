with tmp_STOCK_DAILY_QUOTATION as(
	select 
			a.sec_uni_code as 证券统一编码,
			a.sec_name as 证券简称,
			a.sec_code as 证券代码,
			a.td_date as 交易日期,
			a.open_price as 今开,
			a.pre_close as 昨收,
			a.high_price as 最高,
			a.low_price as 最低,
			a.trans_vol as 成交量
	from STOCK_DAILY_QUOTATION a 
	where concat(a.sec_uni_code, a.td_date) in 
				(
					select concat(sec_uni_code, max(td_date)) 
					from STOCK_DAILY_QUOTATION 
					group by sec_uni_code
				)
)

select t.* from 
(
	select tmp.com_name, a.* 
	from tmp_STOCK_DAILY_QUOTATION a left join (select distinct com_name, sec_name, sec_code from SEC_FIN_INDI_DERIVATIVE)tmp
	on a.证券代码 = tmp.sec_code
)t
where t.com_name is not null

============================================================================================================================

update `公司行情数据` set 交易日期 = replace(交易日期, '.000', '')


============================================================================================================================

drop table if exists `company_market_data`;

create table `company_market_data`
as
select
		a.id as company_id,
		(case when b.交易日期 is null then now() else b.交易日期 end) as trade_date,
		(case when b.今开 is null then '0' else round(b.今开, 2) end) as open_price,
		(case when b.昨收 is null then '0' else round(b.昨收, 2) end) as pre_close,
		(case when b.最高 is null then '0' else round(b.最高, 2) end) as high_price,
		(case when b.最低 is null then '0' else round(b.最低, 2) end) as low_price,
		(case when b.成交量 is null then '0' else round(b.成交量 *10000) end) as trans_vol,
		a.name as name
from company a, `公司行情数据` b
where a.name = b.`com_name`


============================================================================================================================


load csv from "file:///company_market_data.csv" as line
merge (:MarketData {companyEntryId:line[0],
                           tradeDate:line[1],
                           openPrice:line[2],
                           preClose:line[3],
                           highPrice:line[4],
                           lowPrice:line[5],
                           transVol:line[6],
                           name:line[7]
                           })
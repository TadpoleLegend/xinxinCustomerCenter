use ls;
SELECT lc.name, lcs.city_id,lcs.url,lcs.resourceType from ls_city lc,ls_city_url lcs where lc.id=lcs.city_id and lcs.resourceType='1';
SELECT lc.name,lcs.city_id,lcs.url,lcs.resourceType from ls_city lc,ls_city_url lcs where lc.id=lcs.city_id and lcs.resourceType='2';
SELECT lc.name,lcs.city_id,lcs.url,lcs.resourceType from ls_city lc,ls_city_url lcs where lc.id=lcs.city_id and lcs.resourceType='3';

select count(*) from ls_city_url;

select * from 
(select temp138.city_id,temp138.name,temp138.resourceType as source138,temp58.resourceType as source58,temp58.url as url58 from
(SELECT lc.name,lcs.city_id,lcs.url,lcs.resourceType from ls_city lc,ls_city_url lcs where lc.id=lcs.city_id and lcs.resourceType='1' ) as temp138
left join
(SELECT lc.name,lcs.city_id,lcs.url,lcs.resourceType from ls_city lc,ls_city_url lcs where lc.id=lcs.city_id and lcs.resourceType='2' ) as temp58
on temp138.city_id=temp58.city_id) as temp
where temp.url58 is null;

select count(*) from 
(select temp138.city_id,temp138.name,temp138.resourceType as source138,tempedit.resourceType as editsource,tempedit.url as editurl from
(SELECT lc.name,lcs.city_id,lcs.url,lcs.resourceType from ls_city lc,ls_city_url lcs where lc.id=lcs.city_id and lcs.resourceType='1' ) as temp138
left join
(SELECT lc.name,lcs.city_id,lcs.url,lcs.resourceType from ls_city lc,ls_city_url lcs where lc.id=lcs.city_id and lcs.resourceType='2' ) as tempedit
on temp138.city_id=tempedit.city_id) as temp
where temp.editurl is null;


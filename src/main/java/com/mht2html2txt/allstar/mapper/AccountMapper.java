package com.mht2html2txt.allstar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mht2html2txt.allstar.bean.Account;

/**
 * 持久层接口
 * 
 * @author admin
 *
 */
@Mapper
public interface AccountMapper {
	/**
	 * 根据邮箱查询单名账户
	 * 
	 * @param mailbox
	 * @return
	 */
	Account selectByMailbox(@Param("mailbox") String mailbox);

	/**
	 * 根据id查询单名账户
	 * 
	 * @param columnName
	 * @param value
	 * @return
	 */
	Account selectByID(@Param("id") Integer id);

	/**
	 * 根据等于某个值的字段查询单名账户<br>
	 * <b>使用 <i> ${} 值替换</i> 会有SQL注入风险</b>
	 * 
	 * @param colName
	 * @param val     必须是字符串参数值
	 * @return
	 */
	Account selectColEqVal(@Param("colName") String colName, @Param("val") String val);

	/**
	 * 查询全部账号
	 * 
	 * @return
	 */
	List<Account> selectAllAccounts();

	/**
	 * 改1位账户,据id
	 * 
	 * @param id
	 * @return
	 */
	Integer updateByID(@Param("account") Account account, @Param("id") Integer id);

	/**
	 * 创表
	 * <li>t_account</li>
	 */
	void createTblAccount();

	/**
	 * 查询库表的数量
	 * 
	 * @return
	 */
	Integer count();

	/**
	 * 删除表
	 * 
	 * @return
	 */
	Integer delTblAccount();

	/**
	 * 插入一位
	 * 
	 * @return
	 */
	Integer saveAccount(Account account);
}
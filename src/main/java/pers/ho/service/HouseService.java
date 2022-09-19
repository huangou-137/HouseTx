package pers.ho.service;

import pers.ho.bean.House;
import pers.ho.bean.Order;

import java.util.List;

/**
 * @author 黄欧
 * @Date 2021-03-25 23:15
 * 负责处理登录状态下的房屋信息业务
 */
public interface HouseService {

    /**
     * 根据房产编号（houseId）和登记者ID（uid）查询房子全部详细信息
     * @return 如果返回null,说明该房子不存在，或登记者ID出错。
     */
    House getMyHouse(Integer houseId, Integer uid);

    /**
     * 登记房产（用户操作）
     * @return 房产编号，若返回null则登记失败
     */
    Integer enlistHouse(House house, Integer uid);

    /**
     * @param uid 房主ID
     * @return null为可修改，否则为不能重新登记该房屋的原因
     */
    String canUserReEnlist(Integer houseId, Integer uid);

    /**
     * 根据房屋及房主的ID值重新登记(修改)旧的房子（用户操作）
     * @param id 房屋编号，house中id的会被忽略
     * @param uid 房主编号，house中uid的会被忽略
     * @param house 其中state状态字段会被忽略
     */
    boolean reEnlistHouse(Integer id, Integer uid, House house);

    /**
     * @param uid 用户ID
     * @return 该用户是否能够删除该房子
     */
    boolean canUserDelHouse(Integer houseId, Integer uid);

    /**
     * 根据房屋编号值删除该用户的房子
     * @param txPass 用户输入的交易密码
     */
    boolean delHouseByOwner(Integer id, Integer uid, String txPass);

    /**
     * @return 房主ID为uid所有房子的简略信息
     */
    List<House> listBriefHousesByOwner(Integer uid);

    /**
     * @param state 房屋状态，状态为空 或 越界，默认为所有状态下
     * @return 房主ID为uid、状态为state的所有房子的简略信息
     */
    List<House> listBriefHousesByOwner(Integer uid, Byte state);

    /**
     * @param uid 用户ID
     * @return 用户是否能购买该房屋
     */
    boolean canUserBuyHouse(Integer houseId, Integer uid);

    /**
     * 根据订单ID和买方ID获取欲购买或已购买的房产信息<br/>
     * 限制条件（满足其一即可）：<br/>
     * (1)房产状态为【待售中】<br/>
     * (2)房产状态为【交易中】,订单状态为【卖方接受了交易，正在交易中……】<br/>
     * (3)房产状态为【已出售】,订单状态为【交易完成】
     */
    House getBuyingHouse(Integer orderId, Integer buyerId);

    /**
     * 根据用户ID获取其购买成功的所有的房产及相关订单的简略信息
     */
    List<Order> myBoughtHouseOrders(Integer uid);

    /**
     * 根据房屋编号查询该用户已购买的房产和相对应订单信息<br/>
     * 为空代表该房屋未被该用户成功购买
     */
    Order myBoughtHouseOrder(Integer uid, Integer houseId);

}

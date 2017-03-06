package mixer

import mixer.Models.{DepositAccount, UserAccount}

/**
  * Created by natgreenwald on 3/4/17.
  */
object Mixer {
//  transfer bitcoin to user account in parts
  def transferDepositToUser(d: DepositAccount, list: List[UserAccount]): List[UserAccount] = {
    val target = d.depositAddress.reverse
    val t1 = UserAccount(target, d.balance / 2)
    val t2 = UserAccount(target, d.balance / 2)
    val t3 = UserAccount(target, d.balance % 2)
    t1 :: t2 :: t3 :: list
  }
}

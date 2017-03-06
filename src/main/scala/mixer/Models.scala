package mixer

import akka.http.scaladsl.model.DateTime
import spray.json.{DefaultJsonProtocol, JsObject, JsString, JsValue, RootJsonFormat}
import spray.json._
import org.json4s.{DefaultFormats, FieldSerializer}


/**
  * Created by natgreenwald on 3/3/17.
  */
object Models {


  //  TODO: have json read timestamp so can track transactions
  trait Time {
//        val ts: String = DateTime.now.toString()
  }

//  accounts that user provides into which funds eventually deposited from mixer
  case class UserAccount(address: String, balance: Int = 0) extends Time {
    def changeBalance(amount: Int): UserAccount = {
      copy(address, balance + amount)
    }
  }

  object UserAccount {
    import spray.json.DefaultJsonProtocol._

    implicit val format: RootJsonFormat[UserAccount] =
      jsonFormat2(UserAccount.apply)
  }

//created by mixer for user to deposit their funds
  //  account into which user should deposit their funds, and from which mixer will add to House account
  case class DepositAccount(depositAddress: String, balance: Int) extends Time {
//need to timestamp these objects, need to write Json serializer
    def updateBalance(amount: Int): DepositAccount = copy(balance = balance + amount)
}

  object DepositAccount {
    import spray.json.DefaultJsonProtocol._

    implicit val format: RootJsonFormat[DepositAccount] =
      jsonFormat2(DepositAccount.apply)
  }

  object Time {
    implicit object TimeFormat extends RootJsonFormat[Time] {
      override def write(obj: Time): JsObject = obj match {
        case d: DepositAccount =>
          JsObject(d.toJson.asJsObject.fields + ("type" -> JsString("deposit")))
        case u: UserAccount =>
          JsObject(u.toJson.asJsObject.fields + ("type" -> JsString("user")))
      }
      val dep = "deposit".asInstanceOf[JsValue]
      val usr = "user".asInstanceOf[JsValue]
      override def read(json: JsValue): Time = json.asJsObject.fields.get("type") match {
        case Some(dep) => json.convertTo[DepositAccount]
        case Some(usr) => json.convertTo[UserAccount]
      }
    }
  }



//  object UserServiceJsonProtocol extends DefaultJsonProtocol {
//    implicit val userAccountProtocol = jsonFormat2(UserAccount)
//  }

//  implicit object DepositWriter extends RootJsonWriter[DepositAccount] {
//    override def write(dp: DepositAccount): JsValue = JsObject(
//      "depositAddress" -> dp.depositAddress.toJson,
//      "balance" -> dp.balance.toJson
//    )
//  }


//  object DepositServiceJsonProtocol extends DefaultJsonProtocol {
//  implicit val depositformats = DefaultFormats + FieldSerializer[DepositAccount with Time]()
//    implicit val depositAccountProtocol = jsonFormat2(DepositAccount.apply)
//    implicit object TimeJsonFormat extends RootJsonFormat[Time] {
//    def write(t: Time) = t match {
//      case d: DepositAccount => d.toJson
//    }
//      def read(value: JsValue) = value.asJsObject.fields("kind") match {
//        case JsString("depositAccount") => value.convertTo[DepositAccount]
//      }
//  }
//  }
}

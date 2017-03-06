package mixer

import java.util.concurrent.ConcurrentLinkedDeque

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import Models.{UserAccount, DepositAccount}
import Mixer.transferDepositToUser



/**
  * Created by natgreenwald on 3/3/17.
  */
object House extends App {
  implicit val actorSystem = ActorSystem("rest-api")
  implicit val actorMaterializer = ActorMaterializer()

  val userAccounts = new ConcurrentLinkedDeque[UserAccount]()
  val depAccounts = new ConcurrentLinkedDeque[DepositAccount]()
  var depositAccounts = List[DepositAccount]()
  var uAccounts = List[UserAccount]()

  val route =
    path("accounts") {
      post {
        entity(as[UserAccount]) {
          account =>
            complete {
              userAccounts.add(account)
              s"user account noted, deposit money here: localhost:8080/deposits, with address: ${account.address.reverse}, and balance   "
            }
        }
      } ~
        get {
          complete {
            s"$userAccounts"
          }
        }
    } ~
      path("deposits") {
        get {
          complete {
            s"Deposit accounts secret."
          }
        }
      } ~
      post {
        entity(as[DepositAccount]) {
          account =>
            complete {
              depAccounts.add(account)
              uAccounts = transferDepositToUser(account, uAccounts)
              s"your bitcoins transferred: ${account.balance}, check localhost:8080/accountUpdates for updates  "
            }
        }
      } ~
  path("accountUpdates") {
    get {
      complete {
        s"$uAccounts"
      }
    }
  }
  Http().bindAndHandle(route, "localhost", 8080)
}

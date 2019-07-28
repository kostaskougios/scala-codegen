# CodeGen
This scala library allows scala code generation via transforming other scala code
or by creating code from scratch.

A common use case is to load this in your build tool (i.e. sbt) and configure it
to generate new code via existing project code, i.e. to combine 2 or more case classes
into a generated case class that will contain all fields of the combined case classes.

## Use cases

### Combine the field of case classes (i.e. for combining spark tables)

Assuming we have these 2 (or more) case classes:

```scala
import java.sql.Timestamp

case class User(id: Int, name: String)
case class Purchase(userId: Int, item: String, price: BigDecimal, time: Timestamp)
```

We can then auto-generate this case class which contains all fields of User & Purchase 
apart from userId which is removed because it is a duplicate of id:

```scala
import java.sql.Timestamp

case class UserPurchases(id: Int, name: String, item: String, price: BigDecimal, time: Timestamp)

object UserPurchases
{
	def apply(user: User, purchase: Purchase): UserPurchases = UserPurchases(user.id, user.name, purchase.item, purchase.price, purchase.time)
}
``` 


[Example build.sbt](example/build.sbt) 

The library is based on scala meta.
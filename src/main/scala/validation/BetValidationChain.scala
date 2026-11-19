package validation

object BetValidationChain:

  val validator: BetValidator =
    new StatusValidator(
      Some(
        new ActionValidator(
          Some(
            new BalanceValidator(None)
          )
        )
      )
    )

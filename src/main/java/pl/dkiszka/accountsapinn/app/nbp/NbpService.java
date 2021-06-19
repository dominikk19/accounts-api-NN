package pl.dkiszka.accountsapinn.app.nbp;

import java.math.BigDecimal;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
public interface NbpService {
    BigDecimal getUsdExchangeRate();
}

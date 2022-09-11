package io.prizy.adapters.contest.templating;

import java.io.IOException;
import java.util.Map;

import io.prizy.domain.contest.model.Contest;
import io.prizy.domain.contest.model.Pack;
import io.prizy.domain.contest.ports.ContestTemplateCompiler;
import io.prizy.toolbox.templating.MustacheTemplateCompiler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 5/7/2022 11:05 AM
 */


@Slf4j
@Component
public class MustacheContestTemplateCompiler implements ContestTemplateCompiler {

  private static final String IOS_PLATFORM = "iOS";
  private static final String ANDROID_PLATFORM = "Android";

  @Override
  public String rulesTemplate(Contest contest, String platform) {
    try {
      return MustacheTemplateCompiler.compileTemplate("contest-rules.html", Map.of(
        "merchantSiren", contest.merchant().siren(),
        "merchantName", contest.merchant().name(),
        "merchantAddress", contest.merchant().address(),
        "merchantCapital", contest.merchant().capital(),
        "fromDate", contest.fromDate(),
        "toDate", contest.toDate(),
        "packs", contest.packs().stream()
          .map(pack -> {
            if (pack instanceof Pack.Coupon coupon) {
              return coupon.withLastWinnerPosition(coupon.lastWinnerPosition() - 1);
            } else {
              return ((Pack.Product) pack).withLastWinnerPosition(pack.lastWinnerPosition() - 1);
            }
          })
          .toList(),
        "winnerCount", contest.packs().stream().mapToLong(Pack::lastWinnerPosition).sum(),
        "isIos", IOS_PLATFORM.equals(platform),
        "isAndroid", ANDROID_PLATFORM.equals(platform)
      ));
    } catch (IOException e) {
      log.error("Error while compiling contest rules template", e);
      return null;
    }
  }

}

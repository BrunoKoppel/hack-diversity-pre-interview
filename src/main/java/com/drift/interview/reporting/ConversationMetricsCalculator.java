package com.drift.interview.reporting;

import com.drift.interview.model.Conversation;
import com.drift.interview.model.ConversationResponseMetric;
import com.drift.interview.model.Message;
import java.util.List;

public class ConversationMetricsCalculator {
  public ConversationMetricsCalculator() {}

  /**
   * Returns a ConversationResponseMetric object which can be used to power data visualizations on the front end.
   */
  ConversationResponseMetric calculateAverageResponseTime(Conversation conversation) {
    List<Message> messages = conversation.getMessages();

    long numberOfResponses = 0;
    long firstTimeResponseFromEndUser = 0;
    long totalResponseTime = 0;
    long averageResponseTime = 0;
    int i = 0;

    while(messages.size() > i) {
      if (messages.get(i).isTeamMember() == false) {
        firstTimeResponseFromEndUser = messages.get(i).getCreatedAt();
        while ((messages.get(i).isTeamMember() == false) && (messages.size() > i)) {

          /*
          as a little bonus I thought of showing this in the browser, but I wasn't sure because of how many files
          I would have to change and then send along with the answer. So here it is

          I was trying to find a solution with only one return while managing to get the flow control neat and simple and still have
          the message displayed on the console. If I wasn't sure about adding the bonus because it would result in extra lines but
          I guess I'll just leave it for test units :)
           */

          if(messages.size() - 1 == i)
          {
            System.out.println("Team Member never replied to End User");
            return ConversationResponseMetric.builder()
                    .setConversationId(conversation.getId())
                    .setAverageResponseMs(averageResponseTime)
                    .build();
          }

          i++;

        }

        totalResponseTime += (messages.get(i).getCreatedAt() - firstTimeResponseFromEndUser);
        numberOfResponses += 1;
        averageResponseTime = totalResponseTime / numberOfResponses;
      }

      i++;
      }


    return ConversationResponseMetric.builder()
        .setConversationId(conversation.getId())
        .setAverageResponseMs(averageResponseTime)
        .build();
  }
}

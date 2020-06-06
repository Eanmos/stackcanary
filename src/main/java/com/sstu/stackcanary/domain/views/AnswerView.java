package com.sstu.stackcanary.domain.views;

import com.sstu.stackcanary.domain.Answer;
import com.sstu.stackcanary.domain.User;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AnswerView {
   final private Integer id;
   final private UserView author;
   final private String formattedCreationDateTime;
   final private Integer votes;
   final private String bodyInHTML;
   final private boolean votedUpByActiveUser;
   final private boolean votedDownByActiveUser;

   public AnswerView(final Answer a, final User u) {
      this.id = a.getId();
      this.author = new UserView(a.getAuthor());
      this.formattedCreationDateTime = formatCreationDateTime(a.getCreationDateTime());
      this.votes = calculateVotes(a);
      this.bodyInHTML = convertBodyFromMarkdownToHTML(a.getBody());
      this.votedUpByActiveUser = isVotedUpByActiveUser(a, u);
      this.votedDownByActiveUser = isVotedDownByActiveUser(a, u);
   }

   private String formatCreationDateTime(final Date d) {
      DateFormat fmt = new SimpleDateFormat("MMM d ''yy 'at' HH:mm");
      return fmt.format(d);
   }

   private Integer calculateVotes(final Answer a) {
      return a.getVotedUpByUsers().size() - a.getVotedDownByUsers().size();
   }

   private String convertBodyFromMarkdownToHTML(final String markdown) {
      Node document = Parser.builder().build().parse(markdown);
      HtmlRenderer renderer = HtmlRenderer.builder().escapeHtml(true).build();

      return renderer.render(document);
   }

   private boolean isVotedUpByActiveUser(final Answer a, final User u) {
      if (u == null)
         return false;

      return u.getVotedUpAnswers().contains(a);
   }

   private boolean isVotedDownByActiveUser(final Answer a, final User u) {
      if (u == null)
         return false;

      return u.getVotedDownAnswers().contains(a);
   }
}

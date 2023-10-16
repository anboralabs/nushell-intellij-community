// This is a generated file. Not intended for manual editing.
package co.anbora.labs.nushell.community.lang.core.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static co.anbora.labs.nushell.community.lang.core.psi.NuShellTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class NuShellParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType root_, PsiBuilder builder_) {
    parseLight(root_, builder_);
    return builder_.getTreeBuilt();
  }

  public void parseLight(IElementType root_, PsiBuilder builder_) {
    boolean result_;
    builder_ = adapt_builder_(root_, builder_, this, null);
    Marker marker_ = enter_section_(builder_, 0, _COLLAPSE_, null);
    result_ = parse_root_(root_, builder_);
    exit_section_(builder_, 0, marker_, root_, result_, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType root_, PsiBuilder builder_) {
    return parse_root_(root_, builder_, 0);
  }

  static boolean parse_root_(IElementType root_, PsiBuilder builder_, int level_) {
    return File(builder_, level_ + 1);
  }

  /* ********************************************************** */
  // !<<eof>> item_*
  static boolean File(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "File")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = File_0(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && File_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, NuShellParser::recover_any);
    return result_ || pinned_;
  }

  // !<<eof>>
  private static boolean File_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "File_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !eof(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // item_*
  private static boolean File_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "File_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!item_(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "File_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // BOOL_LITERAL | OTHER_IDENTIFIER | SYSTEM |
  //                     NULL | ALIAS | LET | CONST |
  //                     MUT | WHILE | TRY | RETURN |
  //                     LOOP | FOR | IF | ELSE | CONTINUE |
  //                     BREAK | DEF | ENDS_WITH | STARTS_WITH |
  //                     BIT_SHR | BIT_SHL | BIT_XOR | BIT_AND |
  //                     BIT_OR | XOR | OR | AND | NOT | NOT_IN |
  //                     IN | MOD | LINE_TERMINATOR
  static boolean item_(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "item_")) return false;
    boolean result_;
    result_ = consumeToken(builder_, BOOL_LITERAL);
    if (!result_) result_ = consumeToken(builder_, OTHER_IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, SYSTEM);
    if (!result_) result_ = consumeToken(builder_, NULL);
    if (!result_) result_ = consumeToken(builder_, ALIAS);
    if (!result_) result_ = consumeToken(builder_, LET);
    if (!result_) result_ = consumeToken(builder_, CONST);
    if (!result_) result_ = consumeToken(builder_, MUT);
    if (!result_) result_ = consumeToken(builder_, WHILE);
    if (!result_) result_ = consumeToken(builder_, TRY);
    if (!result_) result_ = consumeToken(builder_, RETURN);
    if (!result_) result_ = consumeToken(builder_, LOOP);
    if (!result_) result_ = consumeToken(builder_, FOR);
    if (!result_) result_ = consumeToken(builder_, IF);
    if (!result_) result_ = consumeToken(builder_, ELSE);
    if (!result_) result_ = consumeToken(builder_, CONTINUE);
    if (!result_) result_ = consumeToken(builder_, BREAK);
    if (!result_) result_ = consumeToken(builder_, DEF);
    if (!result_) result_ = consumeToken(builder_, ENDS_WITH);
    if (!result_) result_ = consumeToken(builder_, STARTS_WITH);
    if (!result_) result_ = consumeToken(builder_, BIT_SHR);
    if (!result_) result_ = consumeToken(builder_, BIT_SHL);
    if (!result_) result_ = consumeToken(builder_, BIT_XOR);
    if (!result_) result_ = consumeToken(builder_, BIT_AND);
    if (!result_) result_ = consumeToken(builder_, BIT_OR);
    if (!result_) result_ = consumeToken(builder_, XOR);
    if (!result_) result_ = consumeToken(builder_, OR);
    if (!result_) result_ = consumeToken(builder_, AND);
    if (!result_) result_ = consumeToken(builder_, NOT);
    if (!result_) result_ = consumeToken(builder_, NOT_IN);
    if (!result_) result_ = consumeToken(builder_, IN);
    if (!result_) result_ = consumeToken(builder_, MOD);
    if (!result_) result_ = consumeToken(builder_, LINE_TERMINATOR);
    return result_;
  }

  /* ********************************************************** */
  // !(item_)
  static boolean recover_any(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "recover_any")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !recover_any_0(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (item_)
  private static boolean recover_any_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "recover_any_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = item_(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

}

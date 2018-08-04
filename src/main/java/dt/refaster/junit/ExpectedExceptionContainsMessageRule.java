package dt.refaster.junit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.errorprone.refaster.ImportPolicy;
import com.google.errorprone.refaster.annotation.AfterTemplate;
import com.google.errorprone.refaster.annotation.BeforeTemplate;
import com.google.errorprone.refaster.annotation.Placeholder;
import com.google.errorprone.refaster.annotation.UseImportPolicy;
import org.junit.rules.ExpectedException;

abstract class ExpectedExceptionContainsMessageRule {

	@Placeholder abstract void callUnderTest();

	@BeforeTemplate
	void before(ExpectedException rule,
			Class<? extends Throwable> exceptionType,
			String messageSubstring) {
		rule.expectMessage(messageSubstring);
		rule.expect(exceptionType);
		callUnderTest();
	}

	@AfterTemplate
	@UseImportPolicy(ImportPolicy.STATIC_IMPORT_ALWAYS)
	void after(@SuppressWarnings("unused") ExpectedException rule,
			Class<? extends Throwable> exceptionType,
			String messageSubstring) {
		Throwable t = assertThrows(exceptionType,
				() -> callUnderTest());
		assertThat(t.getMessage(), containsString(messageSubstring));
	}
}

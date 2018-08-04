package dt.refaster.junit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.errorprone.refaster.ImportPolicy;
import com.google.errorprone.refaster.annotation.AfterTemplate;
import com.google.errorprone.refaster.annotation.BeforeTemplate;
import com.google.errorprone.refaster.annotation.Placeholder;
import com.google.errorprone.refaster.annotation.UseImportPolicy;
import org.hamcrest.Matcher;
import org.junit.rules.ExpectedException;

abstract class ExpectedExceptionMatchesMessageRule {

	@Placeholder abstract void callUnderTest();

	@BeforeTemplate
	void before(ExpectedException expectedException,
			Class<? extends Throwable> exceptionType,
			Matcher<String> matcher) {
		expectedException.expectMessage(matcher);
		expectedException.expect(exceptionType);
		callUnderTest();
	}

	@AfterTemplate
	@UseImportPolicy(ImportPolicy.STATIC_IMPORT_ALWAYS)
	void after(@SuppressWarnings("unused") ExpectedException rule,
			Class<? extends Throwable> exceptionType,
			Matcher<String> matcher) {
		Throwable t = assertThrows(exceptionType,
				() -> callUnderTest());
		assertThat(t.getMessage(), matcher);
	}
}

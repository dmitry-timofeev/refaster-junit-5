package dt.refaster.junit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.errorprone.refaster.ImportPolicy;
import com.google.errorprone.refaster.annotation.AfterTemplate;
import com.google.errorprone.refaster.annotation.BeforeTemplate;
import com.google.errorprone.refaster.annotation.Placeholder;
import com.google.errorprone.refaster.annotation.UseImportPolicy;
import org.hamcrest.Matcher;
import org.junit.rules.ExpectedException;

@SuppressWarnings({"unused", "Convert2MethodRef"})  // These rules are compiled into refaster rules.
class ExpectedExceptionRule {

	abstract static class ExpectedExceptionContainsMessageRule {

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

	abstract static class ExpectedExceptionMatchesMessageRule {

		@Placeholder abstract void callUnderTest();

		@BeforeTemplate
		void beforeMessageFirst(ExpectedException expectedException,
				Class<? extends Throwable> exceptionType,
				Matcher<String> matcher) {
			expectedException.expectMessage(matcher);
			expectedException.expect(exceptionType);
			callUnderTest();
		}

		@BeforeTemplate
		void beforeTypeFirst(ExpectedException expectedException,
				Class<? extends Throwable> exceptionType,
				Matcher<String> matcher) {
			expectedException.expect(exceptionType);
			expectedException.expectMessage(matcher);
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

	abstract static class ExpectedExceptionMatchesExceptionRule {

		@Placeholder abstract void callUnderTest();

		@BeforeTemplate
		void before(ExpectedException expectedException,
				Matcher<? super Throwable> matcher) {
			expectedException.expect(matcher);
			callUnderTest();
		}

		@AfterTemplate
		@UseImportPolicy(ImportPolicy.STATIC_IMPORT_ALWAYS)
		void after(@SuppressWarnings("unused") ExpectedException rule,
				Matcher<? super Throwable> matcher) {
			Throwable t = assertThrows(Throwable.class,
					() -> callUnderTest());
			assertThat(t, matcher);
		}
	}

	abstract static class ExpectedExceptionTrivialRule {

		@Placeholder abstract void callUnderTest();

		@BeforeTemplate
		void before(ExpectedException rule,
				Class<? extends Throwable> exceptionType) {
			rule.expect(exceptionType);
			callUnderTest();
		}

		@AfterTemplate
		@UseImportPolicy(ImportPolicy.STATIC_IMPORT_ALWAYS)
		void after(@SuppressWarnings("unused") ExpectedException rule,
				Class<? extends Throwable> exceptionType) {
			assertThrows(exceptionType,
					() -> callUnderTest());
		}
	}
}

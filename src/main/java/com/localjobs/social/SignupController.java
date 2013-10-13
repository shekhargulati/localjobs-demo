package com.localjobs.social;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.localjobs.domain.Account;
import com.localjobs.jpa.repository.AccountRepository;

@Controller
public class SignupController {

    private final AccountRepository accountRepository;

    @Inject
    public SignupController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public SignupForm signupForm(WebRequest request) {
        Connection<?> connection = ProviderSignInUtils.getConnection(request);
        if (connection != null) {
            request.setAttribute("message",
                    new Message(MessageType.INFO, "Your " + StringUtils.capitalize(connection.getKey().getProviderId())
                            + " account is not associated with a JobsNearYou account. If you're new, please sign up."),
                    WebRequest.SCOPE_REQUEST);
            return SignupForm.fromProviderUser(connection.fetchUserProfile());
        } else {
            return new SignupForm();
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Valid SignupForm form, BindingResult formBinding, WebRequest request) {
        if (formBinding.hasErrors()) {
            return null;
        }
        Account account = createAccount(form, formBinding);
        if (account != null) {
            SignInUtils.signin(account.getUsername());
            ProviderSignInUtils.handlePostSignUp(account.getUsername(), request);
            return "redirect:/home";
        }
        return null;
    }

    // internal helpers

    private Account createAccount(SignupForm form, BindingResult formBinding) {
        try {
            String[] skills = uncapitalizeArrayElements(getSkills(form.getSkills()));
            Account account = new Account(form.getUsername(), form.getPassword(), form.getFirstName(),
                    form.getLastName(), form.getAddress(), Arrays.asList(skills));
            accountRepository.save(account);
            return account;
        } catch (Exception e) {
            formBinding.rejectValue("username", "user.duplicateUsername", "already in use");
            return null;
        }
    }

    private static String[] uncapitalizeArrayElements(String[] arr) {
        List<String> elements = new ArrayList<String>();
        for (String element : arr) {
            elements.add(element.toLowerCase());
        }
        return elements.toArray(new String[0]);
    }

    private static String[] getSkills(String skillsStr) {
        String[] skills = skillsStr.split(",");
        skills = StringUtils.trimArrayElements(uncapitalizeArrayElements(skills));
        return skills;
    }

}

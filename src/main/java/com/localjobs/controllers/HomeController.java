package com.localjobs.controllers;

import java.security.Principal;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.localjobs.domain.Account;
import com.localjobs.domain.JobVo;
import com.localjobs.jpa.repository.AccountRepository;
import com.localjobs.service.CoordinateFinder;
import com.localjobs.service.LocalJobsService;
import com.localjobs.utils.SecurityUtils;

@Controller
public class HomeController {

    private final Provider<ConnectionRepository> connectionRepositoryProvider;

    private final AccountRepository accountRepository;

    private LocalJobsService localJobsService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    public HomeController(Provider<ConnectionRepository> connectionRepositoryProvider,
            AccountRepository accountRepository, LocalJobsService localJobsService) {
        this.connectionRepositoryProvider = connectionRepositoryProvider;
        this.accountRepository = accountRepository;
        this.localJobsService = localJobsService;
    }

    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public String home(Principal currentUser, Model model) throws Exception {

        Account account = accountRepository.findAccountByUsername(currentUser.getName());

        double[] coordinates = CoordinateFinder.getLatLng(account.getAddress());
        double latitude = coordinates[0];
        double longitude = coordinates[1];

        List<JobVo> recommendedJobs = recommendedJobs(latitude, longitude, account.getSkills().toArray(new String[0]));

        logger.info("Found jobs " + recommendedJobs.size());

        List<JobVo> appliedJobs = appliedJobs(latitude, longitude, SecurityUtils.getCurrentLoggedInUsername());

        model.addAttribute("connectionsToProviders", getConnectionRepository().findAllConnections());
        model.addAttribute(account);
        model.addAttribute("recommendedJobs", recommendedJobs);
        model.addAttribute("appliedJobs", appliedJobs);
        return "home";
    }

    private List<JobVo> appliedJobs(double latitude, double longitude, String user) {
        return localJobsService.appliedJobs(latitude, longitude, user);
    }

    private List<JobVo> recommendedJobs(double latitude, double longitude, String[] skills) throws Exception {

        List<JobVo> jobs = localJobsService.recommendJobs(latitude, longitude, skills,
                SecurityUtils.getCurrentLoggedInUsername());
        return jobs;
    }

    private ConnectionRepository getConnectionRepository() {
        return connectionRepositoryProvider.get();
    }

}

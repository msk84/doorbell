package net.msk.doorbell.controller;

import net.msk.doorbell.service.EventLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {

    private final EventLogService eventLogService;

    public TemplateController(final EventLogService eventLogService) {
        this.eventLogService = eventLogService;
    }

    @GetMapping("/")
    public String homePage(final Model model) {
        model.addAttribute("event_log", this.eventLogService.getEventLog());
        return "index";
    }

    @GetMapping("/settings")
    public String settingsPage(final Model model) {
        return "settings";
    }

    @GetMapping("/test")
    public String testPage(final Model model) {
        return "test";
    }
}

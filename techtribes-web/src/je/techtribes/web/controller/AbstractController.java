package je.techtribes.web.controller;

import je.techtribes.component.contentsource.ContentSourceComponent;
import je.techtribes.component.log.LoggingComponent;
import je.techtribes.domain.ContentSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;

public class AbstractController {

    protected LoggingComponent loggingComponent;
    protected ContentSourceComponent contentSourceComponent;

    @Autowired
    public void setContentSourceComponent(ContentSourceComponent contentSourceComponent) {
        this.contentSourceComponent = contentSourceComponent;
    }

    @Autowired
    public void setLoggingComponent(LoggingComponent loggingComponent) {
        this.loggingComponent = loggingComponent;
    }

    protected void addCommonAttributes(ModelMap model) {
        model.addAttribute("peopleAndTribes", contentSourceComponent.getPeopleAndTribes());

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            model.addAttribute("user", lookupContentSourceBySignedInTwitterId());
        }
    }

    protected void setPageTitle(ModelMap model, String... titles) {
        String pageTitle = "techtribes.je";
        if (titles != null) {
            for (String title : titles) {
                if (title != null && !title.isEmpty()) {
                    pageTitle = pageTitle + " - " + title;
                }
            }
        }

        model.addAttribute("pageTitle", pageTitle);
    }

    protected ContentSource lookupContentSourceBySignedInTwitterId() {
        String twitterId = SecurityContextHolder.getContext().getAuthentication().getName();
        return contentSourceComponent.findByTwitterId(twitterId);
    }

}

package com.gjz.controller.admin;

import com.gjz.pojo.Blog;
import com.gjz.pojo.Comment;
import com.gjz.pojo.User;
import com.gjz.service.BlogService;
import com.gjz.service.CommentService;
import com.gjz.service.TagService;
import com.gjz.service.TypeService;
import com.gjz.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String List = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";


    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/blogs")
    public String list(@PageableDefault(size = 3, sort = ("updateTime"), direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return List;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 3, sort = ("updateTime"), direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model) {
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());

        return INPUT;
    }

    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);

        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null) {
            attributes.addFlashAttribute("message", "????????????");
        } else {
            attributes.addFlashAttribute("message", "????????????");
        }

        return REDIRECT_LIST;
    }


    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlogTags(id);
        blogService.deleteBlogComments(id);
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "????????????");
        return REDIRECT_LIST;
    }


    @GetMapping("/comment/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("blogId", blogId);
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "admin/comment";
    }


    @GetMapping("/deleteComment/{blogId}/{commentId}")
    public String deleteComment(@PathVariable Long blogId, @PathVariable Long commentId, Model model) {
        commentService.deleteComments(commentId);
        model.addAttribute("blogId", blogId);
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "admin/comment";
    }
}

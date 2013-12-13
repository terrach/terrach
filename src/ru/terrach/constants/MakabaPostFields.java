package ru.terrach.constants;

public class MakabaPostFields {
	private String captchaKey;
	private String video;
	private String noFile;
	private String subject;
	private String submit;
	private String file;
	private String name;
	private String task;
	private String captcha;
	private String email;
	private String comment;

	public String getCaptchaKey() {
		return this.captchaKey;
	}

	public void setCaptchaKey(String captchaKey) {
		this.captchaKey = captchaKey;
	}

	public String getVideo() {
		return this.video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getNoFile() {
		return this.noFile;
	}

	public void setNoFile(String noFile) {
		this.noFile = noFile;
	}

	public String getSubmit() {
		return this.submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTask() {
		return this.task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getCaptcha() {
		return this.captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public static MakabaPostFields getDefault() {
		MakabaPostFields defPF = new MakabaPostFields();
		defPF.setCaptcha("captcha_value_id_06");
		defPF.setCaptchaKey("captcha");
		defPF.setComment("shampoo");
		defPF.setEmail("nabiki");
		defPF.setFile("file");
		defPF.setName("akane");
		defPF.setNoFile("nofile");
		defPF.setSubject("kasumi");
		defPF.setSubmit("submit");
		defPF.setTask("post");
		defPF.setVideo("video");
		return defPF;
	}
}

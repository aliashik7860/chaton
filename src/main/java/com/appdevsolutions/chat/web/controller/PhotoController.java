package com.appdevsolutions.chat.web.controller;

import org.springframework.stereotype.Controller;

@Controller
/*@MultipartConfig(fileSizeThreshold=20971520)*/
public class PhotoController {
	
	/*@Autowired
	@Qualifier("inPhotoService")
	PhotoService<InPhotoModel> inPhotoService;
	

	private static final Logger LOGGER=LoggerFactory.getLogger(PhotoController.class);
	
	@Autowired
	FileUploadBeanValidator fileUploadBeanValidator;
	
	@RequestMapping(value=ChatOnWebConstants.URL_PHOTO_UPLOAD,method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> upload(@RequestParam(ChatOnWebConstants.USER_ID) final String userId,@RequestParam("senderId") final String senderId,@RequestParam("myPhoto") final MultipartFile multipartFile,final HttpServletRequest request){
		LOGGER.debug("request received for upload");
		final ValidationErrorDTO validationErrorDTO=new ValidationErrorDTO();
		validationErrorDTO.setUrl(request.getRequestURL().toString());
		final FileUploadBean fileUploadBean=new FileUploadBean(userId, senderId, multipartFile);
		fileUploadBeanValidator.validate(fileUploadBean, validationErrorDTO);
		if(validationErrorDTO.getNoOfFieldErrors()>0) {
			return new ResponseEntity<ValidationErrorDTO>(validationErrorDTO,HttpStatus.BAD_REQUEST);
		}else {
			final ImageType imageType=ImageType.parse(multipartFile.getContentType());
			try {
				final InPhotoModel inPhotoModel=new InPhotoModel("", userId, senderId, multipartFile.getOriginalFilename(), imageType, multipartFile.getSize(), multipartFile.getBytes(), LocalDateTime.now());
				inPhotoService.savePhoto(inPhotoModel);
			} catch (FileNotFoundException e) {
				final Response response=new Response("", "", "Photo File not found");
				final ChatOnResponse<Set<UserModel>> chatOnResponse=new ChatOnResponse<Set<UserModel>>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.OK.toString(),HttpStatus.OK,ResponseStatus.FAILURE,response);
				return new ResponseEntity<Response>(failureBean, HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (UserNotFoundException e) {
				final Response failureBean=new Response(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, "", "", "Internal Server has occurred : "+e.getMessage());
				return new ResponseEntity<Response>(failureBean, HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (IOException e) {
				final Response failureBean=new Response(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, "", "", "Internal Server has occurred : "+e.getMessage());
				return new ResponseEntity<Response>(failureBean, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>("",HttpStatus.OK);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_PHOTO_BY_PHOTO_ID,method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> downloadPhoto(@PathVariable(ChatOnWebConstants.PHOTO_ID) String photoId,@RequestParam(value="download",required=false) boolean download, HttpServletRequest request) throws Exception{
		LOGGER.debug("request received for download photo info");
		if(photoId==null||photoId.equals("")) {
			final Response failureBean=new Response(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, "photoId", photoId, "");
			return new ResponseEntity<Response>(failureBean,HttpStatus.BAD_REQUEST);
		}else if(download){
			InPhotoModel inPhotoModel=null;
			try {
				inPhotoModel = inPhotoService.findPhoto(photoId,true);
				final MediaType mediaType=MediaType.parseMediaType(inPhotoModel.getContentType().getValue());
				final HttpHeaders headers=new HttpHeaders();
				headers.setContentType(mediaType);
				headers.set(ChatOnWebConstants.DOWNLOAD_CONTENT_DISPOSITION_HEADER_KEY,ChatOnWebConstants.DOWNLOAD_FILENAME_HEADER_KEY +inPhotoModel.getName());
				headers.setContentLength(inPhotoModel.getBytes().length);
				final InputStream inputStream=new ByteArrayInputStream(inPhotoModel.getBytes());
				final InputStreamResource inputStreamResource=new InputStreamResource(inputStream);
				return new ResponseEntity<InputStreamResource>(inputStreamResource,headers,HttpStatus.OK);
			} catch (PhotoNotFoundException e) {
				final Response failureBean=new Response(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, "photoId", photoId, "");
				return new ResponseEntity<Response>(failureBean,HttpStatus.BAD_REQUEST);
			}
		}else{
			InPhotoModel inPhotoModel=null;
			try {
				inPhotoModel = inPhotoService.findPhoto(photoId,false);
				return new ResponseEntity<InPhotoModel>(inPhotoModel,HttpStatus.OK);
			} catch (PhotoNotFoundException e) {
				final Response failureBean=new Response(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, "photoId", photoId, "");
				return new ResponseEntity<Response>(failureBean,HttpStatus.BAD_REQUEST);
			}
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_PHOTO_BY_PHOTO_ID,method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> deletePhoto(@PathVariable(ChatOnWebConstants.PHOTO_ID) String photoId, HttpServletRequest request){
		LOGGER.debug("request received for download photo info");
		if(photoId==null||photoId.equals("")) {
			final Response failureBean=new Response(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, "photoId", photoId, "Photo id is empty");
			return new ResponseEntity<Response>(failureBean,HttpStatus.BAD_REQUEST);
		}else{
			try {
				inPhotoService.deletePhoto(photoId);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (PhotoNotFoundException photoNotFoundException) {
				final Response failureBean=new Response(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, "photoId", photoId, photoNotFoundException.getMessage());
				return new ResponseEntity<Response>(failureBean,HttpStatus.BAD_REQUEST);
			} catch (FileNotFoundException fileNotFoundException) {
				final Response failureBean=new Response(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, "photoId", photoId, fileNotFoundException.getMessage());
				return new ResponseEntity<Response>(failureBean,HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (UserNotFoundException userNotFoundException) {
				final Response failureBean=new Response(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, "photoId", photoId, userNotFoundException.getMessage());
				return new ResponseEntity<Response>(failureBean,HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (IOException ioException) {
				final Response failureBean=new Response(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, "photoId", photoId, ioException.getMessage());
				return new ResponseEntity<Response>(failureBean,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
	
	
	@RequestMapping(value=ChatOnWebConstants.URL_PHOTO_ALL,method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> downloadAllPhotos(HttpServletRequest request) throws Exception{
		LOGGER.debug("request received for download all photos info");
		try {
			final List<InPhotoModel> inPhotoModels = inPhotoService.findAllPhotos();
			return new ResponseEntity<List<InPhotoModel>>(inPhotoModels,HttpStatus.OK);
		} catch (PhotoNotFoundException e) {
			final Response failureBean=new Response(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, "all", "*", "");
			return new ResponseEntity<Response>(failureBean,HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	@RequestMapping(value=ChatOnWebConstants.URL_PROFILE_PHOTO_BY_USER_ID,method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> downloadPhotoByUserId(@PathVariable(ChatOnWebConstants.USER_ID) String userId,HttpServletRequest request){
		LOGGER.debug("request received for download photo info");
		if(userId==null||userId.equals("")) {
			final Response failureBean=new Response(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, "userId", userId, "User Id can not be empty");
			return new ResponseEntity<Response>(failureBean,HttpStatus.BAD_REQUEST);
		}else{
			try {
				final List<InPhotoModel> inPhotoModels = inPhotoService.findAllPhotoByUserId(userId);
				return new ResponseEntity<List<InPhotoModel>>(inPhotoModels,HttpStatus.OK);
			} catch (PhotoNotFoundException e) {
				final Response failureBean=new Response(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, "userId", userId, e.getMessage());
				return new ResponseEntity<Response>(failureBean,HttpStatus.BAD_REQUEST);
			}
		}
	}
	
	
	@RequestMapping(value=ChatOnWebConstants.URL_PHOTO_BY_USER_ID_AND_SEARCH_TEXT,method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> downloadPhotoByUserIdAndSearchText(@PathVariable(ChatOnWebConstants.USER_ID) String userId,@PathVariable(ChatOnWebConstants.SEARCH_TEXT) String searchText,HttpServletRequest request){
		LOGGER.debug("request received for download photos of userId : "+userId+" and searchText : "+searchText);
		if(userId==null||userId.equals("")) {
			final Response failureBean=new Response(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, "userId", userId, "User Id can not be empty");
			return new ResponseEntity<Response>(failureBean,HttpStatus.BAD_REQUEST);
		}
		if(searchText==null||searchText.equals("")) {
			final Response failureBean=new Response(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, "userId", userId, "Search Text can not be empty");
			return new ResponseEntity<Response>(failureBean,HttpStatus.BAD_REQUEST);
		}
		else{
			try {
				final List<InPhotoModel> inPhotoModels = inPhotoService.findAllPhotoByText(userId,searchText);
				return new ResponseEntity<List<InPhotoModel>>(inPhotoModels,HttpStatus.OK);
			} catch (PhotoNotFoundException e) {
				final Response failureBean=new Response(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, "userId", userId, e.getMessage());
				return new ResponseEntity<Response>(failureBean,HttpStatus.BAD_REQUEST);
			}
		}
	}*/
}

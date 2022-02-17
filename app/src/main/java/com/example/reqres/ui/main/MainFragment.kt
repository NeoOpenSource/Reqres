package com.example.reqres.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.reqres.R
import com.example.reqres.api.Config
import com.example.reqres.model.DataRepository
import com.example.reqres.model.UiResponseState
import com.example.reqres.model.UserInformation
import com.example.reqres.module.createRetrofit
import io.reactivex.rxjava3.kotlin.subscribeBy
import org.koin.androidx.viewmodel.ext.android.viewModel
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ViewState()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.readUserData()
    }

    @Composable
    private fun ViewState() {
        when (val state = viewModel.listLiveData.observeAsState().value) {
            is UiResponseState.Error -> {
                DialogShow(
                    "ViewState error:${state.throwable.localizedMessage}",
                    "message:${state.throwable.message}"
                )
            }
            is UiResponseState.Loading -> {
                CircularIndeterminateDialogProgress(state.message)
            }

            is UiResponseState.Success -> {
                BarkHomeContent(state.item)
            }
        }
    }

    @Composable
    private fun BarkHomeContent(list: List<UserInformation>) {
        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
            itemsIndexed(items = list) { index, item ->
                PuppyListItem(puppy = item)
            }
        }
    }

    @Composable
    private fun PuppyListItem(puppy: UserInformation) {
        Card(
            modifier = Modifier
                .padding(8.dp, 4.dp)
                .fillMaxWidth()
                .height(110.dp),
            shape = RoundedCornerShape(8.dp), elevation = 4.dp
        ) {
            Surface {
                Row(
                    Modifier
                        .padding(4.dp)
                        .fillMaxSize()
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = puppy.avatar,

                            builder = {
                                scale(Scale.FILL)
                                placeholder(R.drawable.ic_launcher_background)
                                transformations(CircleCropTransformation())
                            }
                        ),
                        contentDescription = puppy.id,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.2f)
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxHeight()
                            .weight(0.8f)
                    ) {
                        Text(
                            text = puppy.first_name,
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = puppy.last_name,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier
                                .background(
                                    Color.LightGray
                                )
                                .padding(4.dp)
                        )
                        Text(
                            text = puppy.email,
                            style = MaterialTheme.typography.body1,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }

}